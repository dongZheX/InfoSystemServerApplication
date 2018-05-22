package _import;

import javax.swing.JFileChooser;
public class MyFileFilter extends javax.swing.filechooser.FileFilter{
  public boolean accept(java.io.File f) {
    if (f.isDirectory())return true;
    return f.getName().endsWith(".xls");  //设置为选择以.xls为后缀的文件
  } 
  public String getDescription(){
    return ".xls";
  }
}