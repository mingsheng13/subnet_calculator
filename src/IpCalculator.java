import java.util.Arrays;

public class IpCalculator
{
    private final String inputIp;
    private final String subnetMask;
    private final char[][] networkID = new char[4][8];
    private String networkIdInDecimal;
    private String[] ip;
    private String[] mask;

    public IpCalculator(String inputIp, String subnetMask) {
        this.inputIp = inputIp;
        this.subnetMask = subnetMask;
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
    public void calculate()
    {
        ip = toBinary(inputIp);
        mask = toBinary(subnetMask);
        char[][] ipInCharArr = toChar(ip);
        char[][] maskInCharArr = toChar(mask);

        //bit-wise AND
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
    public void print()
    {
        System.out.println("IP: \t\t\t"+ inputIp);
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
    }

    public static void main(String[] args)
    {
        IpCalculator ip1 = new IpCalculator("1.1.1.1", "255.0.0.0");
        ip1.calculate();
        ip1.print();
    }
}
