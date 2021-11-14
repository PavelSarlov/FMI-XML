<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head></head>
            <body>
                <table border="1px solid">
                    <xsl:for-each select=".//track">
                        <tr>
                            <xsl:choose> 
                                <!-- Ако дължината е по-голяма от 15 -->
                                <xsl:when test="string-length() &gt; 15">
                                    <td>A big string</td>
                                </xsl:when>
                                <!-- Ако дължината е по-малка от 15 -->
                                <xsl:when test="string-length() &lt; 15">
                                    <td>A small string</td>
                                </xsl:when>
                                <!-- В противен случай -->
                                <xsl:otherwise>
                                    <td>A medium string</td>
                                </xsl:otherwise>
                            </xsl:choose>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
