# Documentation

# Models

## Day

#### Long Id
#### private double morningSells
#### private double afternoonSells
#### private double totalSells
#### private boolean holiday
#### private Month month

<br>

## Month

### Long Id
### private double goal
### private String name
### private List<Day> days
### private Year year
### <br>
### public double totalMorningSells()
##### returns the sum of total morning sells going trough every day

### public double totalAfternoonSells()
##### returns the sum of total afternoon sells going through every day

### public int daysPassed()

### public int HdDaysPassed()

### public int BdDaysPassed()

### public double totalHolidaySells()

### public double totalBdSells()

### public double averageSells()

### public double averageBDsells()

### public double averageHDsells()

### public double averageMorningsells()

### public double averageAfternoonsells()

### public double remainingDays()

<br>

## Year

### Long Id

### private int number

### private List <Month> months


## Services