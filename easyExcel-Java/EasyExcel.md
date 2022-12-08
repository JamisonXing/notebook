# EasyExcel

## 读数据

1. pom.xml

```xml
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>easyexcel</artifactId>
            <version>3.1.1</version>
        </dependency>
```

2. 读取数据

```java
    public void readTest01() {
        //读取文件
        //创建ExcelReaderBuilder实例
        ExcelReaderBuilder readerBuilder = EasyExcel.read();
        //获取文件对象
        readerBuilder.file("D:\\JackeyXingCoder\\javaGithubProject\\student统计表.xls");
        //指定sheet,不指定解析默认全部
        readerBuilder.sheet("xxj统计表");
        //自动关闭输入流
        readerBuilder.autoCloseStream(true);
        //设置Excel文件格式
        readerBuilder.excelType(ExcelTypeEnum.XLS);
        //注册监听器进行数据的解析
        readerBuilder.registerReadListener(new AnalysisEventListener<Object>() {

            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                //一行数据读取完成后回调
                System.out.println(o);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                //文件读取完毕执行
                System.out.println("文件读取完毕");
            }
        });
        //构建读取器
        ExcelReader reader = readerBuilder.build();
        //读取数据
        reader.readAll();
        //读取完毕
        reader.finish();
    }
```

输出结果

![](C:\Users\28109\AppData\Roaming\marktext\images\2022-09-08-10-17-31-image.png)

可以通过泛型指定数据类型，Map集合

```java
    public void readTest01() {
        //读取文件
        //创建ExcelReaderBuilder实例
        ExcelReaderBuilder readerBuilder = EasyExcel.read();
        //获取文件对象
        readerBuilder.file("D:\\JackeyXingCoder\\javaGithubProject\\student统计表.xls");
        //指定sheet,不指定解析默认全部
        readerBuilder.sheet("xxj统计表");
        //自动关闭输入流
        readerBuilder.autoCloseStream(true);
        //设置Excel文件格式
        readerBuilder.excelType(ExcelTypeEnum.XLS);
        //注册监听器进行数据的解析
        readerBuilder.registerReadListener(new AnalysisEventListener<Map<Integer,String>>() {
            @Override
            public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
                //一行数据读取完成后回调
                Set<Integer> keySet = integerStringMap.keySet();
                for (Integer key : keySet) {
                    System.out.println(key + ":" + integerStringMap.get(key) + ", ");
                }
                System.out.println("");
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                //文件读取完毕执行
                System.out.println("文件读取完毕");
            }
        });
        //构建读取器
        ExcelReader reader = readerBuilder.build();
        //读取数据
        reader.readAll();
        //读取完毕
        reader.finish();
    }
```

输出结果

![](C:\Users\28109\AppData\Roaming\marktext\images\2022-09-08-10-36-51-image.png)

实际开发中，对上述代码进行简化

```java
    public void readTest02() {
        List<Map<Integer, String>> list = new LinkedList<>();
        EasyExcel.read("D:\\JackeyXingCoder\\javaGithubProject\\student统计表.xls")
                .sheet()
                .registerReadListener(new AnalysisEventListener<Map<Integer, String>>() {
                    @Override
                    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
                        list.add(integerStringMap);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("数据读取完毕");
                    }
                }).doRead();

        for (Map<Integer, String> integerStringMap : list) {
            Set<Integer> keySet = integerStringMap.keySet();
            for (Integer key : keySet) {
                System.out.println(key + ":" + integerStringMap.get(key) + ", ");
            }
            System.out.println("");
        }
    }
```

输出结果

![](C:\Users\28109\AppData\Roaming\marktext\images\2022-09-08-10-39-26-image.png)

3. 映射成指定对象，需要创建实体类，使用@ExcelProperty完成实体类成员变量和Excel字段的映射

```java

package readTest.dao;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExcelData {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("学号")
    private String id;
    @ExcelProperty("时间")
    private Date date;
}
```

```java
    @Test
    public void readTest03() {
        List<ExcelData> list = new LinkedList<>();
        EasyExcel.read("D:\\JackeyXingCoder\\javaGithubProject\\student统计表.xls")
                .head(ExcelData.class)
                .sheet()
                .registerReadListener(new AnalysisEventListener<ExcelData>() {
                    @Override
                    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
                        list.add(excelData);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("数据读取完毕");
                    }
                }).doRead();

        for (ExcelData excelData : list) {
            System.out.println(excelData);
        }
    }
```

输出结果

![](C:\Users\28109\AppData\Roaming\marktext\images\2022-09-08-10-54-09-image.png)

## 写数据

```java
public class writeTest {
    public static List<ExcelData> parseData() {
        List<ExcelData> list = new LinkedList<>();
        EasyExcel.read("D:\\JackeyXingCoder\\javaGithubProject\\student统计表.xls")
                .head(ExcelData.class)
                .sheet()
                .registerReadListener(new AnalysisEventListener<ExcelData>() {
                    @Override
                    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
                        list.add(excelData);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("数据读取完毕");
                    }
                }).doRead();
        return list;
    }

    @Test
    public void writeTest01() {
        List<ExcelData> list = parseData();
        EasyExcel.write("D:\\JackeyXingCoder\\javaGithubProject\\student统计表_副本.xls")
                .head(ExcelData.class)
                .excelType(ExcelTypeEnum.XLS)
                .sheet("xxj统计表_副本")
                .doWrite(list);
    }

}
```
