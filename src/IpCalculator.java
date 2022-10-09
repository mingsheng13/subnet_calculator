import java.util.Arrays;
import java.util.Scanner;

public class IpCalculator
{
    private String inputIp;
    private String subnetMask;
    private final char[][] networkID = new char[4][8];
    private String networkIdInDecimal;
    private String[] ip;
    private String[] mask;
    private String minId;
    private String maxId;
    private String broadcastId;
    private int total_hostId;

    public IpCalculator()
    {
        System.out.println("Please enter ip address in the following format:\n192.168.159.1/24\n192.168.132.3/18\n192.156.3.6/20\n...");
        System.out.print("Enter IP: ");
        Scanner input = new Scanner(System.in);
        String inputIp = input.nextLine();
        inputProcess(inputIp);
        calculate();
        print();
    }
    public void inputProcess(String input)  //192.168.0.1/23
    {
        String[] tmp = input.split("/");
        inputIp = tmp[0];
        
        int subnet = Integer.parseInt(tmp[1]);
        char[][] subnetArrInChar = new char[4][8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if(subnet >= 1)
                {
                    subnetArrInChar[i][j] = '1';
                    subnet--;
                }
                else
                    subnetArrInChar[i][j] = '0';
            }
        }
        subnetMask = toDecimal(subnetArrInChar);
    }

    public String[] toBinary(String input)
    {
        String[] ipInString = input.trim().split("\\.");   //split ipv4 with . into a string arr
        String[] ipInBinary = new String[4];        //a string arr for storing ipv4 in binary

        int[] iIp = new int[4];
        for (int i = 0; i < ipInString.length; i++)
        {
            iIp[i] = Integer.parseInt(ipInString[i]);       //parse string to int
            ipInBinary[i] = Integer.toBinaryString(iIp[i]);     //convert int to binary in string format
        }
        //add zero(s) into front of binary
        for (int i = 0; i < ipInBinary.length; i++) {
            while(ipInBinary[i].length() < 8)
            {
                ipInBinary[i] = "0" + ipInBinary[i];
            }
        }

        return ipInBinary;
    }
    public char[][] toChar(String[] input)
    {
        char[][] arr = new char[4][8];
        for (int i = 0; i < 4; i++) {
            arr[i] = input[i].toCharArray();
        }
        return arr;
    }
    public String toDecimal(char[][] input)
    {
        String[] str = new String[4];
        int[] arr = new int[4];
        for (int i = 0; i < input.length; i++)
        {
            str[i] = String.valueOf(input[i]);
            arr[i] = Integer.parseInt(str[i], 2);
        }
        return Arrays.toString(arr).replace(",",".").replace("[","").replace("]","").replace(" ","");
    }
    public void calculate()
    {
        ip = toBinary(inputIp);
        mask = toBinary(subnetMask);
        char[][] ipInCharArr = toChar(ip);
        char[][] maskInCharArr = toChar(mask);

        //bitwise AND
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(ipInCharArr[i][j] == maskInCharArr[i][j])
                    networkID[i][j] = ipInCharArr[i][j];
                else
                    networkID[i][j] = '0';
            }
        }
        networkIdInDecimal = toDecimal(networkID);

        //min id
        networkID[3][7] = '1';
        minId = toDecimal(networkID);
        //broadcast id
        int iFlag = 0, jFlag = 0;
        boolean b = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if(maskInCharArr[i][j] == '0')
                {
                    iFlag = i;
                    jFlag = j;
                    b = true;
                    break;
                }
            }
            if(b)
                break;
        }
        boolean startOne = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if(i == iFlag && j == jFlag)
                    startOne =true;
                if(startOne)
                    networkID[i][j] = '1';
            }
        }
        broadcastId = toDecimal(networkID);
        //max id
        networkID[3][7] = '0';
        maxId = toDecimal(networkID);

    }

    public void print()
    {
        System.out.println("IP: \t\t"+ inputIp);
        System.out.print("IP in binary: \t");
        for (int i = 0; i < 4; i++)
        {
            System.out.print(ip[i]+ " ");
        }
        System.out.println();
        System.out.println("Subnet Mask: \t"+subnetMask);
        System.out.print("Mask in binary: ");
        for (int i = 0; i < 4; i++) {
            System.out.print(mask[i]+ " ");
        }
        System.out.println();
        System.out.println("Network ID: \t"+networkIdInDecimal);
        System.out.println("Broadcast ID: \t"+broadcastId);
        System.out.println("Min ID: \t"+minId);
        System.out.println("Max id: \t"+maxId);
    }

    public static void main(String[] args)
    {
        System.out.println();
        IpCalculator ip1 = new IpCalculator();
    }
}
