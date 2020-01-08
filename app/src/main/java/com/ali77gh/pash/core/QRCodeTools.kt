package com.ali77gh.pash.core

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*


object QRCodeTools {

    fun generate(content: String, cb :(bitmap:Bitmap)->Unit) {

        Thread{
            val hints = Hashtable<EncodeHintType,String>()
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8"

            val writer = QRCodeWriter()
            try {
                val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512, hints)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                    }
                }
                cb(bmp)

            } catch (e: WriterException) {
                e.printStackTrace()
                throw RuntimeException()
            }
        }.start()
    }
}