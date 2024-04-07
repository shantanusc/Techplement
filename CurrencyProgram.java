package com.ssc.p;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CurrencyProgram {
    private static Scanner scan = new Scanner(System.in);

    private static ArrayList<String> currencyName = new ArrayList<>();
    static{
        currencyName.add("USD");
        currencyName.add("INR");
        currencyName.add("EUR");
        currencyName.add("CAD");
        currencyName.add("HKD");
    }

    public static void main(String[] args) throws IOException, JSONException {


        Scanner scan = new Scanner(System.in);
        System.out.println("\\WELL COME TO THE CONNCERY CONVERTER\\");
        System.out.println("-------------------------------------");

        display();


    }
    public static void display() throws JSONException, IOException {
        System.out.println("CHOOSE ONE OF THESE OPTION");
        System.out.println("1 for convert");
        System.out.println("2 add your favorite once");
        System.out.println("3 for view favorite once");
        System.out.println("4 for updating the favorite once");
        System.out.println("5 exit\n");
        System.out.print("enter the option in number:");
        int num = scan.nextInt();
        method(num);
    }
    public static void method(int num) throws IOException, JSONException {
        switch (num){
            case 1:convertCurrency(); break;
            case 2:addFavorite(); break;
            case 3: viewCurrency(); break;
            case 4:updateCurrency();break;
            case 5 :exit(); break;
            default:
                System.out.println("please enter the correct option");
                display();break;

        }
    }

    static void exit() {
        System.out.println("thank you");
    }

    public static void convertCurrency() throws IOException, JSONException {



        String convertFrom , convertTo;
        double amount;


        System.out.println(CurrencyProgram.currencyName);
        System.out.print("currency converting From:");
        convertFrom = scan.next().toUpperCase();
//        convertFrom = currencyName.get(scan.nextInt());
        System.out.println(CurrencyProgram.currencyName);
        System.out.print("currency converting To:");
        convertTo = scan.next().toUpperCase();
//        convertTo = currencyName.get(scan.nextInt());
        System.out.println("Amount you want to convert:");
        amount = scan.nextDouble();

        sendHTTPGetRequest(convertFrom, convertTo, amount);


    }
    public static void sendHTTPGetRequest(String convertFrom, String convertTo, double amount) throws IOException, JSONException {
//        final String ACCESS_KEY = "9fdf399ce61a5f4ef04f5f6b5347ec7d";
        final String GET_URL = "https://v6.exchangerate-api.com/v6/b257260aec0f09d5c3aabb9c/latest/USD";
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder responce = new StringBuilder();

            while ((inputLine = in.readLine())!=null){
                responce.append(inputLine);
            }in.close();
//            System.out.println(responce);

            String jsonresponce = responce.toString();
            JSONObject jsonObject = new JSONObject(jsonresponce);
//            Extract exchange rate for the target currency from the JSON response

            double exchangeRate = jsonObject.getJSONObject("conversion_rates").getDouble(convertFrom);
            System.out.println(jsonObject.getJSONObject("conversion_rates"));
            System.out.println(exchangeRate);//debbuging
            System.out.println();
            double convertedRate = amount/exchangeRate;
            System.out.printf(amount +" "+ convertFrom + " = " + convertedRate +" "+ convertTo);
            System.out.println();
            CurrencyProgram.display();

        }else {
            System.out.println("don't get");
        }

    }
    public static void addFavorite() throws JSONException, IOException {
        System.out.print("Enter the currency name:");
        String newCurrencyName = scan.next().toUpperCase();
        if(!currencyName.contains(newCurrencyName)){
            currencyName.add(newCurrencyName);
            System.out.println("currency " + newCurrencyName +" is added");
        }else {
            System.out.println("currency is alredy exits");
        }
        CurrencyProgram.display();
    }
    public static void viewCurrency() throws JSONException, IOException {
        if(currencyName.isEmpty()){
            System.out.println("no courrency is avilable");
        }else{
            System.out.print("available currency are:");
            for (String currency:currencyName){
                System.out.println(currency.toUpperCase());
            }
        }
        System.out.println("hello i am in viewCurrency");
        CurrencyProgram.display();
    }
    public static  void updateCurrency() throws JSONException, IOException {
        if (currencyName.isEmpty()){
            System.out.println("no currency is added yet");
        }else{
            System.out.println("Enter the currency");
            String oldCurrency = scan.next().toUpperCase();
            if(currencyName.contains(oldCurrency)){
                System.out.println("Enter the new currency");
                String newCurrency = scan.next().toUpperCase();
                System.out.println(oldCurrency+ " update to "+ newCurrency);
            }else{
                System.out.println("currency does't exit");
            }
        }
        CurrencyProgram.display();

    }
}
