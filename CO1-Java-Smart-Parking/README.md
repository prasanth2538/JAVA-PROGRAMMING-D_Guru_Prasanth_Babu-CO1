# Campus Smart-Parking System (CO1 Java)

A console-based Java implementation of the assignment's campus smart-parking scenario.

## Requirements
- JDK 17 or later (uses switch expressions and pattern matching for `instanceof`)
- Visual Studio Code with the Java Extension Pack

## Folder structure
- `src/parking/model`: vehicles, customers, tickets and parking slots
- `src/parking/service`: parking allocation and exit/billing service
- `src/parking/app`: console application

## Compile and run in VS Code terminal
Open the project folder, then run:

```bash
javac -d out src/parking/model/*.java src/parking/service/*.java src/parking/app/*.java
java -cp out parking.app.Main
```

On Windows PowerShell, the same commands work if the JDK `bin` folder is in PATH.

## Implemented features
- Jagged floor/slot structure (different slot counts per floor)
- Vehicle hierarchy: TwoWheeler, Car, ElectricCar, EBike, DeliveryVan
- `Chargeable` interface for EV charging
- Student/staff monthly pass and visitor one-day pass handling
- Unique ticket numbering using a static counter
- Duplicate registration validation
- Slot allocation and release
- Fee calculation using integer ceiling division
- Delivery van double rate for billable hours starting from 9:00 through 16:59
- EV charging at Rs 12 per kWh
- HashMap for active ticket lookup
- Encapsulation, inheritance, overriding, abstraction and runtime polymorphism

## Assumptions
- The sample facility has 3 floors with 5, 6 and 4 slots. Change the array in `Main` to configure it.
- The first slot on each floor is an EV charging slot; remaining slots are standard slots.
- Rates: two-wheeler Rs 10/hour; car and electric car Rs 30/hour; delivery van base Rs 30/hour; e-bike flat Rs 15 per visit.
- Every started hour is billed in full. Zero-minute parking has zero parking fee.
- Delivery-van hourly rate is doubled when the billable hour starts between 09:00 and 16:59.
- Valid student/staff monthly passes and visitor one-day passes waive parking charges; EV charging charges remain separate.
- A visitor one-day pass is valid on the exact date entered.
- Charging is available only to `ElectricCar` in this implementation.

## Notes for assignment report
For Tasks 1-10, include the design explanation, UML diagram, pseudocode and complexity comparisons requested in the PDF. Task 11 should include test evidence. Task 12 should explain the changed requirements and defend the extensible design.
