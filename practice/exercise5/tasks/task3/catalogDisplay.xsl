<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head></head>
            <body>
                <h1>В нарастващ ред</h1>
                <table border="1px solid">
                    <xsl:for-each select=".//track">
                        <!-- Сортираме по съдържанието, по подразбиране е във възходящ ред -->
                        <xsl:sort select="text()"/>
                        <tr>
                            <td><xsl:value-of select="text()"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h1>В намаляващ ред</h1>
                <table border="1px solid">
                    <xsl:for-each select=".//track">
                        <!-- Сортираме по съдържанието, този път в низходящ ред -->
                        <xsl:sort order="descending" select="text()"/>
                        <tr>
                            <td><xsl:value-of select="text()"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h1>В нарастващ или намаляващ ред в следния формат: track_1, track_2,..., track_n</h1>
                <xsl:for-each select=".//track">
                    <!-- Сортираме по съдържанието, по подразбиране е във възходящ ред -->
                    <xsl:sort select="text()"/>
                    <!-- Взимаме стойностите в указания формат, внимаваме на последния елемент да не слагаме запетайка -->
                    <xsl:value-of select="text()"/>_<xsl:value-of select="position()"/>
                    <xsl:if test="position() != last()">, </xsl:if>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
