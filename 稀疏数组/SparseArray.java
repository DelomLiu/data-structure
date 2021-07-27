import java.io.*;

public class SparseArray {
    public static void main(String[] args) throws IOException {
        //创建一个原始的二维数组11*11
        //0代表没有棋子，1代表黑子，2代表篮子
        int[][] chessArray=new int[11][11];
        chessArray[1][2]=1;
        chessArray[2][3]=2;
        chessArray[4][5]=2;

        //输出原始的二维数组
        System.out.println("原始的二维数组：");
        for(int[] row : chessArray) {
            for(int data : row) {
                System.out.print(data + "\t");
            }
            System.out.println();
        }

        //二维数组 转 稀疏数组
        //1、遍历原始的二维数组，得到非0数据的个数
        int sum=0;
        for(int i=0;i<11;i++) {
            for(int j=1;j<11;j++) {
                if(chessArray[i][j]!=0)
                    sum++;
            }
        }

        //2、创建对应的稀疏数组
        int sparseArray[][]=new int[sum+1][3];
        //给稀疏数组赋值
        sparseArray[0][0]=11;
        sparseArray[0][1]=11;
        sparseArray[0][2]=sum;

        //遍历二维数组，将非0数据放到sparseArray中
        int count=0; //用于记录是第几个非0数据
        for(int i=0;i<11;i++) {
            for(int j=0;j<11;j++) {
                if(chessArray[i][j]!=0) {
                    count++;
                    sparseArray[count][0]=i;
                    sparseArray[count][1]=j;
                    sparseArray[count][2]=chessArray[i][j];
                }
            }
        }

        System.out.println();
        //输出稀疏数组
        System.out.println("得到的稀疏数组：");
        for(int i=0;i<sparseArray.length;i++) {
            System.out.println(sparseArray[i][0]+"\t"+sparseArray[i][1]+"\t"+sparseArray[i][2]);
        }
        System.out.println();

        //将稀疏数组保存到棋盘上，并命名为map.data
        //创建map.data
        File file=new File("数据结构/稀疏数组/map.data");
        if(file.exists()) {
            System.out.println("文件已经存在");
        } else {
            try {
                file.createNewFile();
                System.out.println("文件创建成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //写入
        FileWriter f1=null;
        try {
            f1=new FileWriter(file);//创建文件写入对象
            for(int i=0;i<sparseArray.length;i++) {
                for(int j=0;j<3;j++) {
                    f1.write(sparseArray[i][j]+"\t");
                }
                f1.write("\r\n");
            }
            f1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //从map.data里面读取稀疏数组
        BufferedReader bufferedReader=null;
        //为读取的稀疏数组分配空间
        int[][] datas=new int[sparseArray.length][3];
        try {
            InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(file));
            bufferedReader=new BufferedReader(inputStreamReader);
            String line=null;
            int i=0;
            //按行读取
            while((line=bufferedReader.readLine())!=null) {
                if(line!=null) {
                    //将按行读取的字符串按空格分割，得到一个string数组
                    String[] strings=line.split("\\t");
                    //依次转换为int类型存入到分配好空间的数组中
                    for(int k=0;k<strings.length;k++) {
                        datas[i][k]=Integer.valueOf(strings[k]);
                    }
                    //行数加1
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将稀疏数组 恢复成 二维数组
        //1、先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        int newArray[][]=new int[datas[0][0]][datas[0][1]];

        //2、读取稀疏数组后几行的数据，并赋给原始的二维数组
        for(int i=1;i<datas.length;i++) {
            newArray[datas[i][0]][datas[i][1]]=datas[i][2];
        }

        System.out.println();
        //输出恢复后的二维数组
        System.out.println("恢复后的二维数组：");

        for(int[] row : newArray) {
            for(int data : row) {
                System.out.print(data+"\t");
            }
            System.out.println();
        }
    }
}
