package com.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelAndByteBufferWriteAndRead {
    private static final int BSIZE = 1024; //размер буфера
    private static final String fileName = "data.txt";
    public static void main(String[] args) throws IOException {

        //WRITING TO FILE data.txt
        //первый способ с помощью FileOutputStream
        FileChannel fc = new FileOutputStream(fileName).getChannel();//связывание файла с каналом
        fc.write(ByteBuffer.wrap("Some text".getBytes()));//запись текста Some text на файл
        fc.close();//необходимо закрыть канал после записи

        //второй способ с помощью RandomAccessFile
        fc = new RandomAccessFile(fileName, "rw").getChannel();
        fc.position(fc.size());//установка курсора на конец файла, чтобы записать после всех данных
        fc.write(ByteBuffer.wrap(" Some more text".getBytes()));
        fc.close();

        //READING FROM FILE data.txt
        fc = new FileInputStream(fileName).getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);//установливаем размер буфера
        fc.read(buff);//куда записать прочитанные данные
        buff.flip();//чтобы извлечь из буфера данные
        while (buff.hasRemaining())//если есть какие-то элементы между текущей позицией и пределом
            System.out.print((char)buff.get());//получаем и выводим
        fc.close();

    }
}
