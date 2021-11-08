<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="https://www.w3.org/2006/xpath-functions">
   <xsl:template match="/">
      <html>
         <body>
            <h2>1. Стойностите на всички track елементи, чиито атрибут length е равен на '4:04' и са включени в елемент cd, имащ id равно на 8c0a600b</h2>
            <xsl:value-of select='//cd[@id="8c0a600b"]//track[@length="4:04"]'/>

            <h2>2. Всички track елементи на четни/нечетни позиции</h2>
            <xsl:value-of select='//track[position() mod 2 = 0]'/><br/>
            <xsl:value-of select='//track[position() mod 2 != 0]'/>

            <h2>3. Стойностите на всички track елементи, чиято дължина на текста е по-голяма от 35</h2>
            <xsl:value-of select='//track[string-length(text()) > 35]'/>

            <h2>4. Дължината на текста на всички track елементи, чиято дължина на текста е по-голяма от 15</h2>
            <xsl:value-of select='string-length(//track[string-length(text()) > 15]/text())'/>

            <h2>5. Последния track елемент от всяко cd</h2>
            <xsl:value-of select='//cd//track[last()]'/>

            <h2>6. Петия track елемент от всяко cd</h2>
            <xsl:value-of select='//cd//track[position()=5]'/>

            <h2>7. Броя на track елементите за всяко cd</h2>
            <xsl:for-each select='//cd'>
                <xsl:value-of select='count(.//track)'/><br/>
            </xsl:for-each>

            <h2>8. Всички track елементи, които съдържат 'Ya soshla s uma'</h2>
            <xsl:for-each select='//track[contains(text(), "Ya soshla s uma")]'>
                <xsl:value-of select='text()'/><br/>
            </xsl:for-each>

            <h2>9. Всички track елементи, които започват с буквата 'D'</h2>
            <xsl:for-each select='//track[starts-with(text(), "D")]'>
                <xsl:value-of select='text()'/><br/>
            </xsl:for-each>

            <h2>10. Всички track елементи, които завършват с израза 'sta'</h2>
            <xsl:for-each select='//track[fn:#ends-with(text(), "sta")]'>
                <xsl:value-of select='text()'/><br/>
            </xsl:for-each>

            <!-- <h2>11. Стойностите на всички track елементи, разпечатани с главни букви</h2> -->
            <!-- <xsl:value-of select='//track/upper-case(text())'/> -->

            <!-- <h2>12. Стойността на елемента year, който е под-елемент на елемента cd, съдържащ под-под-елемент track с length = '3:55' и имащ стойност 'Robot (Robotronik)'</h2> -->
            <!-- <xsl:value-of select='//cd[.//track[@length="3:55" and text()="Robot (Robotronik)"]]/year/text()'/> -->

            <!-- <h2>13. Среден брой track елементи от всички налични cd елементи</h2> -->
            <!-- <xsl:value-of select='count(//cd//track) div count(//cd)'/> -->

            <!-- <h2>14. За всеки елемент cd изведете стойността на под-елементите му title и year, спазвайки следния модел: -->
            <!--         Заглавие: title_value; Година на издаване: year_value</h2> -->
            <!-- <xsl:value-of select='//cd/concat("Заглавие: ", title, "; Година на издаване: ", year)'/> -->
         </body>
      </html>
   </xsl:template>
</xsl:stylesheet> 
