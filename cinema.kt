package cinema

fun main() {
    println("Enter the number of rows:")
    val numberOfRows = readLine()!!.toInt()
    
    println("Enter the number of seats in each row:")
    val numberOfSeatsInRow = readLine()!!.toInt()

    val arr = Array(numberOfRows) { CharArray(numberOfSeatsInRow){'S'} }
    
    do {
        printMenu()
        val command = readLine()!!.toInt()
        when (command) {
            1 -> printCinema(arr)
            2 -> buyTicket(arr)
            3 -> printStatistics(arr)
        }
    } while (command != 0)
}

fun printStatistics(arr: Array<CharArray>) {
    val purchasedTickets = getPurchasedTickets(arr);
    val totalSeats = getTotalSeats(arr);
    val percentage = "%.2f".format(100.0 * purchasedTickets / totalSeats)
    
    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: ${percentage}%")
    println("Current income: $${getCurrentIncome(arr)}")
    println("Total income: $${getTotalIncome(arr)}")
    println()
}

fun getTotalIncome(arr: Array<CharArray>): Int {
    var sum = 0
    for (rowIndex in arr.indices) {
        for (seatIndex in arr[rowIndex].indices) {
            sum += getPrice(arr, rowIndex + 1, seatIndex + 1)
        }
    }
    return sum 
}

fun getCurrentIncome(arr: Array<CharArray>): Int {
    var sum = 0
    for (rowIndex in arr.indices) {
        for (seatIndex in arr[rowIndex].indices) {
            if (arr[rowIndex][seatIndex] == 'B') {
                sum += getPrice(arr, rowIndex + 1, seatIndex + 1)
            }
        }
    }
    return sum
}

fun getPurchasedTickets(arr: Array<CharArray>): Int {
    var count = 0
    for (rowIndex in arr.indices) {
        for (seatIndex in arr[rowIndex].indices) {
            if (arr[rowIndex][seatIndex] == 'B') {
                count += 1
            }
        }
    }
    return count
}

fun getTotalSeats(arr: Array<CharArray>): Int {
    return arr.size * arr[0].size
}

fun getPrice(arr: Array<CharArray>, row: Int, seat: Int): Int {
    val numberOfSeats = arr.size * arr[0].size
    val priceOfFrontTicket = 10
    val priceOfBackTicket = if (numberOfSeats <= 60) 10 else 8
    val frontHalf = arr.size / 2
    val backHalf = arr.size - frontHalf
    
    return if (row > frontHalf) priceOfBackTicket else priceOfFrontTicket
}

fun buyTicket(arr: Array<CharArray>) {
    while(true) {
        println("Enter a row number:")
        val row = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        val seat = readLine()!!.toInt()
        
        if (row > arr.size || seat > arr[0].size) {
            printMessage("Wrong input!")
            continue
        }
        
        if (isPurchasedSeat(arr, row, seat)) {
            printMessage("That ticket has already been purchased!")
            continue
        }
        
        arr[row - 1][seat - 1] = 'B'
        printMessage("Ticket price: $${getPrice(arr, row, seat)}")
        break
    }
}

fun isPurchasedSeat(arr: Array<CharArray>, row: Int, seat: Int): Boolean {
    return arr[row - 1][seat - 1] == 'B'
}

fun printMenu() {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    println()
}

fun printCinema(arr: Array<CharArray>) {
    println("Cinema:")
    print(" ")
    for (n in 1..arr[0].size) {
        print(" $n")
    }
    println()
    
    for (indexRow in arr.indices) {
        print("${indexRow + 1}")
        
        for (seat in arr[indexRow]) {
            print(" $seat")
        }
        println()
    }
    println()
}

fun printMessage(message: String) {
    println()
    println(message)
    println()
}