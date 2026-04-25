# Ethereum Java Web3j Integration Project

這是一個基於 **Java 11+** 與 **Spring Boot** 框架開發的以太坊 (Ethereum) 區塊鏈整合專案。透過 **Web3j** 函式庫，本專案實現了與本地端以太坊節點的溝通，並提供完整的 RESTful API 來進行錢包管理、區塊鏈資料查詢、智能合約部署與互動，以及智能合約事件 (Events) 的監聽與紀錄。

## 🛠️ 技術堆疊 (Tech Stack)

* **後端框架**: Java, Spring Boot (Spring Web)
* **區塊鏈互動**: Web3j (Ethereum JSON-RPC API client)
* **非同步處理**: RxJava (用於智能合約 Event 監聽)
* **資料處理**: Jackson (JSON 轉換), Lombok (減少 Boilerplate 程式碼)
* **智能合約**: Solidity (編譯為 Java Wrapper Classes)

## 🚀 核心功能 (Core Features)

### 1. 錢包與帳戶管理 (Wallet Management)
* **建立新錢包**: 動態生成新的 Keystore 錢包檔案。
* **餘額查詢**: 透過錢包地址查詢帳戶餘額，並支援 Wei、Gwei 與 Ether 單位轉換。
* **驗證錢包**: 驗證特定錢包地址在本地節點中的有效性。

### 2. 區塊鏈資料查詢 (Blockchain Explorer)
* **最新區塊資訊**: 取得區塊高度、區塊 Hash 以及區塊內的交易數量。
* **調閱所有交易**: 遍歷區塊鏈，整理並回傳所有歷史交易紀錄。
* **交易明細查詢 (TxHash)**: 根據 Transaction Hash 取得詳細的交易資訊 (From, To, Value, Gas Price, Gas Limit)。

### 3. 智能合約互動 (Smart Contract Interaction)
* **合約部署**: 支援動態部署編譯好的 Solidity 智能合約 (`SimpleContract` 與 `UserPrice` 業務合約)。
* **資料讀寫**: 呼叫合約內的 View/Pure 方法讀取資料 (`getData`)，或發送交易來寫入資料 (`setData`, `setUser`, `updateUser` 等)。
* **代幣轉帳**: 提供呼叫合約執行轉帳交易 (`transPrice`) 的功能。

### 4. 事件監聽服務 (Event Listening)
* 透過 Web3j 的 `EthFilter` 與 RxJava 的 `Flowable`，持續監聽區塊鏈上的特定智能合約事件。
* 支援監聽包含：使用者註冊 (`UserResgister`)、交易紀錄 (`TransactionRecord`) 以及管理員更新 (`AdminUpdateUserRecode`) 等 Log 事件。
* 將監聽到的區塊鏈事件統一轉交由 `EventService` 保存在記憶體中，供前端隨時調閱。

---

## 📂 專案架構 (Project Structure)

```text
src/main/java/
├── BlockChain/
│   ├── EthereumWeb1Application.java       # Spring Boot 啟動入口
│   ├── EthereumController.java            # REST API 控制器 (負責接收前端請求)
│   ├── EthereumComponent.java             # 基礎區塊鏈互動邏輯 (轉帳、區塊查詢、SimpleContract)
│   └── EthereumComponent_UserPrice.java   # 業務合約互動邏輯 (UserPrice 專屬邏輯)
├── BlockChainObject/
│   ├── BlockClass.java                    # 區塊與交易資料模型 (POJO)
│   ├── BlockUser.java                     # 使用者資料模型
│   ├── EventService.java                  # 區塊鏈事件集中管理服務
│   └── *EventObject.java                  # 各類事件的資料承載實作 (實作 EmitEvent_Interface)
└── lib/
    ├── ApplibConfig.java                  # Spring Bean 初始化設定
    ├── BlockConfig.java                   # 節點、GasProvider、合約地址等核心設定
    └── UserPrice.java                     # Web3j 自動產生的智能合約 Wrapper 類別
```

---

## 🔌 API 列表 (API Endpoints)

所有的 API 皆支援 CORS (`@CrossOrigin`)，主要前綴為 `/EthereumController/`。
*(以下列出部分核心 API)*

* **GET** `/EthereumController/New__Wallet` - 創建新錢包並回傳公私鑰與地址。
* **GET** `/EthereumController/Check_Wallet` - 檢查特定節點錢包餘額。
* **GET** `/EthereumController/View_Last_Brock` - 取得鏈上最新區塊資訊。
* **GET** `/EthereumController/View_Array_Block` - 獲取包含詳細交易資訊的所有區塊陣列 (JSON)。
* **GET** `/EthereumController/View_Transaction_Hash?Hash_Code={hash}` - 根據 TxHash 查詢交易。
* **GET** `/EthereumController/TransFer_ETH?Wallet_Address={address}` - 執行 ETH 轉帳操作。
* **GET** `/EthereumController/Contract_build_UserPrice` - 部署 `UserPrice` 智能合約。
* **POST** `/EthereumController/Contract_setUser` - 呼叫合約新增使用者。
* **GET** `/EthereumController/Contract_getUserApprovLog` - 取得智能合約上的註冊與審核事件日誌。

---

## 🧹 近期重構與 Clean Code 優化 (Recent Refactoring)

為確保專案的可維護性與穩定性，本專案已套用 Clean Code 原則進行全面重構：

1. **命名規範 (Naming Conventions)**：將區域變數與方法參數從 `PascalCase` 全面修正為 Java 標準的 `camelCase`。
2. **封裝性 (Encapsulation)**：所有的 Data Model (如 `BlockClass`, `BlockUser` 等) 皆將屬性設為 `private`，並統一由 Lombok 處理存取，防止外部不當竄改。並補上 `@JsonProperty` 確保 JSON 序列化與前端 API 欄位格式保持相容。
3. **全局例外處理 (Exception Handling / 防呆機制)**：在 Controller 層加入完整的 `try-catch` 機制，捕獲所有底層拋出的例外，並以 `Error Code: 500` 回傳，避免服務崩潰；資料存取內部操作中也增加了 `null` 檢查。
4. **程式碼清理 (Code Cleanup)**：移除未使用到的套件 (Imports)、冗餘變數 (Unused Variables) 以及反模式寫法。
5. **註解國際化**：將原始的繁體中文開發註解全面翻譯為標準英文註解，以利團隊維護。

---

## 💻 啟動與設定 (Setup & Installation)

1. **節點準備**: 專案預設連接兩個本地以太坊節點 (例如 Geth 或 Hardhat node)，預設埠為 `8085` 與 `8084`。
2. **環境變數調整**: 請至 `lib/BlockConfig.java` 修改您的本地 Keystore 路徑與合約位址。
3. **編譯與啟動專案**: 使用 Maven 執行 `mvn clean install` 與 `mvn spring-boot:run` 啟動伺服器。
