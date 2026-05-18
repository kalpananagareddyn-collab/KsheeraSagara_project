# Ksheera-Sagara — Dairy Profit Calculator (Android)

Modern Android app built with **Kotlin + Jetpack Compose + Material 3**.

## Open in Android Studio

1. Unzip the file.
2. Open **Android Studio** (Hedgehog / Iguana or newer).
3. Choose **File → Open** and select the unzipped `KsheeraSagara` folder.
4. Let Gradle sync (first sync downloads Gradle 8.7 + dependencies).
5. Click ▶ **Run** on an emulator or device (min SDK 24).

## Stack
- Kotlin 1.9.24
- AGP 8.5.2
- Jetpack Compose BOM 2024.08
- Material 3 + Navigation Compose
- ViewModel state holder (in-memory)

## Screens
- **Login** — username / password / continue as guest
- **Dashboard** — add milk entry, expenses, calculate profit
- **Profit** — donut chart, revenue & expense breakdown
- **Cow Analysis** — ranking, add cow, top performer
- **Monthly** — 6-month trend bar chart + share report

## Notes
- Data is held in memory via `DairyViewModel` and seeded with sample entries on launch.
- To persist across launches, swap the in-memory lists for Room or DataStore.
