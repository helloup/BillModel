# 基础还款模型

---

该模型有如下概念：

 - `Item` 对应一个明细项，而 `Charge` 收费可以对于一期账单（账单包括很多收费项），也可以对于一笔账单（一笔账单包括多期账单）
 - `Item` 有**应还金额**、**实还金额**、**待还金额**和**可还金额**，其中**待还金额为应还金额减实还金额**，可还金额在对账单进行结算，才会计算
 - 如果 `Charge` 包含多个 `Item`，就不允许包含 `Charge`，同样如果包含多个 `Charge`，就不允许包含 `Item`
 - `Item` 和 `Charge`可以具有 `priority` 优先级，`priority`在对 `Charge` 进行结算可能会产生作用，有些结算策略会忽略优先级
 - `Item` 在结算后，如果**待还金额**和**可还金额**相等，认为 `Item` 为结清

---

## 结算策略 ##

---

如果 `Charge` 包含多个 `Item`，结算策略为 `SettleItem`；如果 `Charge` 包含多个 `Charge`，结算策略为 `SettleCharge`

 - `SettleItem` 明细项结算策略
  - `TOTAL`：所有子 `Item` 必须还清
  - `SINGLE`：从有效级高的开始结算，单个子 `Item` 必须还清
  - `NONE`：从有效级高的开始结算，单个子 `Item` 够还多少就还多少
  - `SINGLE_MORE`：忽略有效级，从待还金额大的开始结算，单个子 `Item` 必须还清
  - `NONE_MORE`：忽略有效级，从待还金额大的开始结算，单个子 `Item` 够还多少就还多少
  - `SINGLE_LESS`：忽略有效级，从待还金额小的开始结算，单个子 `Item` 必须还清
  - `NONE_LESS`：忽略有效级，从待还金额小的开始结算，单个子 `Item` 够还多少就还多少
 - `SettleCharge` 收费结算策略
  - `TOTAL`：所有子Charge必须还清，将忽略明细项结算策略、子Charge收费结算策略
  - `SINGLE`：从有效级高的开始结算，单个子Charge必须还清，将忽略明细项结算策略、子Charge收费结算策略固定为TOTAL
  - `NONE`：从有效级高的开始结算，单个子Charge够还多少就还多少
  - `SINGLE_IGNORE`：忽略优先级开始结算，单个子Charge必须还清，将忽略明细项结算策略、子Charge收费结算策略固定为TOTAL
  - `NONE_IGNORE`：忽略优先级开始结算，单个子Charge够还多少就还多少

---

## 监控器 ##

---

监控器的作用，可以对感兴趣的多个 `Item` 或者多个 `Charge` 进行监控，可以获取监控器结果 `MonitorResult`。对 `Item` 进行监控可以使用 `MonitorItem`，对 `Charge` 进行监控可以使用 `MonitorCharge`。在 `Charge` 和 `Item` 中使用方法 `addMonitor` 添加监控器，返回的是 `Charge` 和 `Item` 在监控器中的**索引**，所以可以通过索引在监控器中获取 `Charge` 和 `Item`。

监控器结果 `MonitorResult` 有**应还金额**、**实还金额**、**待还金额**和**可还金额**四个属性，还具有一个方法 `isComplete` 认为**待还金额**和**可还金额**相等返回 `true`。 

`MonitorItem` 可以监控多个 `Item`，获取的 `MonitorResult`聚合了多个 `Item` 的**应还金额**、**实还金额**、**待还金额**和**可还金额**。`MonitorCharge` 必须指定一个 `Charge` 的索引来获取该 `Charge` 的监控结果，该监控结果聚合了 `Charge` 内所有的后代 `Item` 的属性。

监控器可以在任意时刻监控，并获取这次监控的监控结果**版本号**，可以通过通过版本号获取监控结果。例如，`MonitorItem` 可以监控多个 `Item`，在结算前获取一次监控结果，在结算后获取一次监控结果。可以通过结算后的监控结果来判断监控的多个 `Item` 是否都结清，这比监控 `Charge` 更具有细粒度和灵活性。也比较两次监控结果来获取资金流向。
