package com.example.a2zbilling.printer;

import android.app.Activity;
import android.widget.Toast;

import com.example.a2zbilling.db.entities.SaleDeatial;
import com.example.a2zbilling.db.entities.Sales;
import com.example.a2zbilling.db.entities.Stock;
import com.leerybit.escpos.DeviceCallbacks;
import com.leerybit.escpos.PosPrinter;
import com.leerybit.escpos.PosPrinter60mm;
import com.leerybit.escpos.Ticket;
import com.leerybit.escpos.TicketBuilder;
import com.leerybit.escpos.bluetooth.BTService;

import java.nio.channels.Pipe;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BTPrinter {
    private static BTPrinter intance;
    private PosPrinter printer;

    public PosPrinter getPrinter() {
        return printer;
    }

    private BTPrinter(final Activity mainActivity){
        printer = new PosPrinter60mm(mainActivity);
        printer.setCharsetName("UTF-8");
        printer.setDeviceCallbacks(new DeviceCallbacks() {
            @Override
            public void onConnected() {
                Toast.makeText(mainActivity,"Disconneted",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure() {
                Toast.makeText(mainActivity, "Connection failed", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onDisconnected() {
                Toast.makeText(mainActivity,"Connect",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void printTicket(List<SaleDeatial> saleDetails, Sales sale) {
        try {
            Date date = new Date();
            String stringDate = new SimpleDateFormat("dd.MM.yyyy").format(date).toString();
            String strTime = new SimpleDateFormat("HH:mm").format(date).toString();
            TicketBuilder builder = new TicketBuilder(printer)
                    .feedLine()
                    .header("Om Prakash ")
                    .header("Electricals & Electronic")
                    .divider()
                    .menuLine("Date: "+stringDate, "Time: "+strTime)
                    .text("Ticket No: "+(sale.getSaleId()))
                    .fiscalInt("ticket_no", sale.getSaleId())
                    .divider()
                    //.subHeader("Hot dishes")
                    .menuLine("Item         Price  Qnt  ", "Value")
                    .divider();

            int totalItems = 0;
            double totalAmount = 0;
            for (SaleDeatial stock:saleDetails
            ) {
                // 10 char
                String name =  getFixedLenString(stock.getSaleDetailItemName(), 10);
                // 1 char
                String sp = " ";

                // 5 char
                String price = getFixedLenString(stock.getSalingPrice(), 5);

                // 1 char

                // 5 char
                String qaunt = getFixedLenString(stock.getQuntity(), 4);

                // 2 char
                String unit = getFixedLenString(stock.getUnit(),2);

                // 1 char
                // 6 char
                double amountDouble = Double.parseDouble(stock.getSalingPrice()) * stock.getQuntity();
                String amount = getFixedLenString( Double.toString(amountDouble), 6);

                builder.menuLine(name+sp,price+sp+qaunt+unit+sp+amount);

                totalItems ++;
                totalAmount += Double.parseDouble(stock.getSalingPrice());

            }
            //.menuLine("Rice           44.00  2kg", "88.00")
            //.menuLine("Dal            20.00  2kg", "40.00")
                        /*.right("Total: 92,00")
                        .feedLine()
                        .subHeader("Salads")
                        .menuLine("- 1 Chicken & Anda", "4,50")
                        .menuLine("- 1 Kadai Paneer", "3,30")
                        .menuLine("- 1 Gajar kakri Salad", "7,00")
                        .right("Total: 14,80")
                        .feedLine()
                        .subHeader("Desserts")
                        .menuLine("- 1 Khasta", "5,00")
                        .menuLine("- 2 Rasgulla", "7,00")
                        .right("Total: 20,00")
                        .feedLine()
                        .subHeader("Drinkables")
                        .center("50% sale for Coke on mondays!")
                        .menuLine("- 3 Coca-Cola", "6,00")
                        .menuLine("- 7 Tea", "3,50")
                        .menuLine("- 2 Coffee", "3,00")
                        .right("Total: 12,50")*/
            builder.dividerDouble()
                    .menuLine("Total Item", ""+totalItems)
                    .menuLine("Total", ""+totalAmount)
                    //.fiscalDouble("gift", 3.0, 2)
                    //.fiscalDouble("price", 131.30, 2)
                    //.fiscalDouble("out_price", 128.30, 2)
                    .dividerDouble()
                    .stared("Thank You !")
                    .feedLine(4);

            builder.isCyrillic(true);
            Ticket ticket = builder.build();

            //preview.setTicket(ticket);
            printer.send(ticket);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static BTPrinter getInstance(Activity mainActivity){
        if(intance == null){
            intance = new BTPrinter(mainActivity);
        }
        if(intance.getPrinter().isConnected() == false){
            intance.getPrinter().connect();
        }
        return intance;
    }

    public static String getFixedLenString(double value, int maxStrLen){
        return getFixedLenString(Double.toString(value), maxStrLen);
    }
    public static String getFixedLenString(String value, int maxStrLen){
        String strWithWhiteSpace = String.format("%"+(-maxStrLen)+"s", value);
        return strWithWhiteSpace.substring(0,maxStrLen);
    }



}
