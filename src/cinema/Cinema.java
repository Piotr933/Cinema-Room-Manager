package cinema;
import java.util.Scanner;

public class Cinema {
    final int one;
    final int startAt;
    final int hundred;
    final char prC;
    final char dollar;
    final int smallRoomSize;
    final int priceFront;
    final int priceBack;


public  Cinema(){
    one = 1;
    startAt = 0;
    hundred = 100;
    prC = '%';
    dollar = '$';
    smallRoomSize = 60;
    priceFront = 10;
    priceBack = 8;
}
    public static void main(String[] args) {

        Cinema cinema = new Cinema();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int nrRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsInRow = scanner.nextInt();
        String[][] cinemaRoom = cinema.drawCinema(nrRows, seatsInRow);
        cinema.Menu(cinemaRoom, nrRows, seatsInRow);


    }
    private boolean availabilityOfTicket(int selectRow , int selectSeat , String[][] cinemaRoom){
        return !cinemaRoom[selectRow][selectSeat].equals("B");

    }
    private  double countPercentage(int nrRows, int seatsInRow, int purchasedTickets) {
        double seats = nrRows * seatsInRow;
        return  (hundred / seats) * purchasedTickets;
    }

    private int totalIncome(int seatsNr, int nrRows, int seatsInRow) {
        int totalIncome;
        if (seatsNr <= smallRoomSize){
            totalIncome = seatsNr * priceFront;
        } else {
            int halfCinema = nrRows / 2;
            int secondHalf = halfCinema;
            if (nrRows % 2 != 0) {
                secondHalf = halfCinema + one;
            }
            totalIncome = (halfCinema * seatsInRow * priceFront) + (secondHalf * seatsInRow * priceBack);
        }
        return  totalIncome;
    }




    private void printStatistic(int purchasedTickets, int nrRows, int seatsInRow, int currentIncome) {
        System.out.printf("Number of purchased tickets: %d", purchasedTickets);
        double percent = countPercentage(nrRows, seatsInRow, purchasedTickets);
        String percentage = String.format("%.2f", percent);
        System.out.println();
        System.out.printf("Percentage: %s%c",percentage,prC);
        System.out.println();
        int seatsNr = nrRows * seatsInRow;
        System.out.printf("Current income: %c%d", dollar, currentIncome);
        System.out.println();
        final int totalIncome = totalIncome(seatsNr, nrRows, seatsInRow);
        System.out.printf("Total income: %c%d",dollar, totalIncome);

    }

    private String[][] drawCinema(int nrRows, int seatsInRow) {
        String[][] cinemaRoom = new String[nrRows + one][seatsInRow
                + one];
        for (int i = startAt; i < cinemaRoom.length; i++) {
            for (int j = startAt; j < cinemaRoom[i].length; j++) {
                if (i == startAt && j == startAt) {
                    cinemaRoom[i][j] = " ";
                    continue;
                }
                if (i == startAt) {
                    cinemaRoom[i][j] = String.valueOf(j);
                    continue;
                }
                if (j == startAt) {
                    cinemaRoom[i][j] = String.valueOf(i);
                } else {
                    cinemaRoom[i][j] = "S";
                }

            }
        }
        return cinemaRoom;
    }

    private void printCinema(String[][] cinemaRoom) {
        System.out.println("Cinema:");
        for (int i = startAt; i < cinemaRoom.length; i++) {
            for (int j = startAt; j < cinemaRoom[i].length; j++) {
                System.out.print(cinemaRoom[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int priceTicket(int nrRows, int seatsInRow, int selectRow) {

        int ticket;
        final int sizeOfRoom = nrRows * seatsInRow;
        int setPriceforFront = nrRows / 2;
        ticket = priceFront;

        if (sizeOfRoom > smallRoomSize) {
            if (selectRow > setPriceforFront) {
                ticket = priceBack;
            }
        }
       return ticket;
    }

    public void Menu(String[][] cinemaRoom,int nrRows, int seatsInRow) {
        Scanner scanner = new Scanner(System.in);
        int purchasedTickets = 0;
        int currentIncome  = 0;
        for (;;) {
            System.out.println();
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int input = scanner.nextInt();
            switch (input){
                case 1:
                    printCinema(cinemaRoom);
                    break;
                case 2:
                    for (;;) {
                        System.out.println("Enter a row number:");
                        int selectRow = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int selectSeat = scanner.nextInt();
                        if (selectRow > nrRows || selectSeat > seatsInRow) {
                            System.out.println("Wrong input!");
                            continue;
                        }
                        boolean availabilityOfTicket = availabilityOfTicket(selectRow,selectSeat,cinemaRoom);
                        if(!availabilityOfTicket){
                            System.out.println("That ticket has already been purchased!");
                            continue;
                        }
                        int ticket =  priceTicket(nrRows, seatsInRow, selectRow);
                        System.out.println("Ticket price: " + "$" + ticket);
                        currentIncome += ticket;
                        cinemaRoom[selectRow][selectSeat] = "B";
                        purchasedTickets += one;
                        break;
                        }
                    break;



                case 3:
                    printStatistic(purchasedTickets, nrRows, seatsInRow, currentIncome);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("wrong input");

            }
        }
    }
}