
```mermaid

flowchart TD
    A[Start: isValidSudoku] --> B{Is board empty?}
    B -->|Yes| C[Return False]
    B -->|No| D[Validate row lengths]
    
    D --> E{All rows = board size?}
    E -->|No| C
    E -->|Yes| F[Calculate subBoxSize]
    
    F --> G{Is size a perfect square?}
    G -->|No| C
    G -->|Yes| H[Initialize tracking sets]
    
    H --> I[Loop through each cell]
    I --> J{Is cell empty?}
    J -->|Yes| N[Continue to next cell]
    J -->|No| K[Parse cell value]
    
    K --> L{Is value valid?}
    L -->|No| C
    L -->|Yes| M{Is value in range?}
    
    M -->|No| C
    M -->|Yes| O[Calculate box index]
    
    O --> P{Value already in row/column/box?}
    P -->|Yes| C
    P -->|No| Q[Add value to tracking sets]
    
    Q --> R{More cells?}
    R -->|Yes| I
    R -->|No| S[Return True]
    
    N --> R
    
    ```