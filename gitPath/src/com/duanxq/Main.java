package com.duanxq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {

        //项目根路径
        if (args == null || args.length == 0 || "".equals(args[0])) {
            System.out.println("项目目录不能为空");
            return;
        }
        if (!new File(args[0]).exists()) {
            System.out.println("项目目录不存在");
            return;
        }
        String rootPath = replaceFs2SysFS(args[0]);
        if (!rootPath.endsWith(File.separator)) {
            rootPath += File.separator;
        }
        String projectName = new File(rootPath).getName();
        System.out.println("项目名称："+projectName);
        System.out.println("项目根路径："+rootPath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("diff.txt")));
            PrintWriter file = new PrintWriter(new File("file.txt"));
            PrintWriter path = new PrintWriter(new File("path.txt"));
            String data_file ;
            String data_path ;
            String temp;
            while (( temp = br.readLine()) != null) {
                if (temp.endsWith(".java")) {
                    //在对应target/classes下面找编译过后的class
                    data_file = temp.replace("src/main/java", "target/classes");
                    data_file = data_file.replace(".java", ".class");
                } else {
                    data_file = temp;
                }
                data_file = rootPath + data_file;
                file.println(replaceFs2SysFS(data_file));


                data_path = temp.replace("src/main/java", "WEB-INF/classes");
                data_path = data_path.replace("src/main/resources", "WEB-INF/classes");
                data_path = data_path.replace("src/main/config", "WEB-INF/classes");
                data_path = data_path.replace("src/main/webapp", "");
                data_path = data_path.replaceAll("[^/]+$", "");
                data_path = projectName + File.separator + data_path;
                path.println(replaceFs2SysFS(data_path));
            }
            file.flush();
            file.close();
            path.flush();
            path.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * 替换字符串中的路径分隔符为系统分隔符
     * @param src 要替换的字符串
     * @return 替换过后的字符串
     */
    private static String replaceFs2SysFS(String src) {
        if ("/".equals(File.separator)) {
            return src.replace("\\", File.separator);
        }
        return src.replace("/", File.separator);

    }


}
