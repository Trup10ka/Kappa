### Features
- [ ] Remove .err. logs and create own logger
- [ ] Add tests
- [ ] When parsing dates, make dots possible to use as well as slashes
- [ ] Implement update for orders
- [x] Add interface for argument parsing
- [ ] Add import / export for customers and products
- [ ] Add aggregate export for users (female / male / other)

### Project tasks
- [ ] Make an example of a 'dirty read' on customer credits
- [ ] Make an example of a 'dirty write' on customer credits

### Bugs
- [ ] Fix the bug where StringUtil parseByTwoSpaces does not work correctly

### Improvements
- [x] Refactor addCustomer method parameters into just a Customer object


### Mandatory requirements
- [x] Musíte použít skutečný relační databázový systém (případně objektově-relační, nelze ale použít jiné typy databází nebo SQLite)
- [ ] Aplikace musí pracovat s databází, která obsahuje minimálně: 5x tabulek (včetně vazebních), 2x pohled (view), 1x vazba M:N
- [x] Mezi atributy tabulek musí být minimálně 1x zastoupen každý z datových typů: Reálné číslo (float), Logická hodnota (bool nebo ekvivalent), Výčet (enum), Řetězec (string, varchar), Datum nebo čas (datetime, date, time)
- [x] Musíte umožnit vložení, smazání, zobrazení a úpravu nějaké informace, která se ukládá do více než jedné tabulky. Například vložení objednávky, která se na úrovni databáze rozloží do tabulek objednavka, zakaznik a polozky
- [x] Do aplikace naprogramovat mininálně jedno použití transakce nad více než jednou tabulkou. Například převod kreditních bodů mezi dvěma účty apod.
- [ ] Pomocí aplikace generovat souhrnný report, který bude obsahovat smysluplná agregovaná data z alespoň tří tabulek. Např. různé počty položek, součty, minima a maxima, apod.
- [ ] Umožnit import dat do min. dvou tabulek z formátu CSV, XML nebo JSON.
- [x] Umožnit nastavovat program v konfiguračním souboru.
- [x] Ošetřit vstupy a připravit chybové hlášky a postupy pro všechna možná selhání, včetně chyb konfigurace, chyb zadání nebo chyb spojení s databází,
