<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head></head>
            <body>
                <!-- Стойностите ще ги извеждам в таблици, за да е малко по-пригледно. -->
                <h2>1) Стойностите на всички track елементи, които имат стойност на атрибута length '4:04'</h2>
                <table border="1px solid">
                    <!-- Обикаляме по всички track елементи -->
                    <xsl:for-each select=".//track">
                        <!-- Изкарваме съдържанието само на тези, които имат атрибут length '4:04' -->
                        <xsl:if test="@length='4:04'"> 
                            <tr>
                                <td><xsl:value-of select="text()"/></td>
                            </tr>
                        </xsl:if>
                    </xsl:for-each>
                </table>

                <h2>2) Стойностите на всички track елементи, чиято дължина е по-малка от 15, както и техните дължини</h2>
                <table border="1px solid">
                    <!-- Обикаляме по всички track елементи -->
                    <xsl:for-each select=".//track">
                        <!-- Изкарваме съдържанието и дължината само на тези, които имат дължина < 15 -->
                        <xsl:if test="string-length() &lt; 15"> 
                            <tr>
                                <td><xsl:value-of select="text()"/></td>
                                <td><xsl:value-of select="string-length()"/></td>
                            </tr>
                        </xsl:if>
                    </xsl:for-each>
                </table>

                <h2>3) Всички track елементи на четни/нечетни позиции</h2>
                <table border="1px solid">
                    <!-- Обикаляме по всички track елементи -->
                    <xsl:for-each select=".//track">
                        <!-- Взимаме четните позиции -->
                        <xsl:if test="position() mod 2 = 0"> 
                            <tr>
                                <td><xsl:value-of select="text()"/></td>
                                <td><xsl:value-of select="position()"/></td>
                            </tr>
                        </xsl:if>
                    </xsl:for-each>

                    <!-- Отделяме един ред за пригледност -->
                    <tr height="20px"/>

                    <!-- Обикаляме по всички track елементи -->
                    <xsl:for-each select=".//track">
                        <!-- Взимаме нечетните позиции -->
                        <xsl:if test="position() mod 2 != 0"> 
                            <tr>
                                <td><xsl:value-of select="text()"/></td>
                                <td><xsl:value-of select="position()"/></td>
                            </tr>
                        </xsl:if>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
