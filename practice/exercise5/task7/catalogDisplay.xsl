<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head></head>
            <body>
                <table border="1px solid">
                    <tr>
                        <th>Year</th>
                        <th>Title</th>
                        <th>Artist</th>
                    </tr>
                    <xsl:for-each select=".//cd">
                        <tr>
                            <!-- Извикваме темплейтите за всяко cd със съответните параметри. -->
                            <td>
                                <xsl:call-template name="year">
                                    <xsl:with-param name="yearName" select="year"/>
                                </xsl:call-template>
                            </td>
                            <td>
                                <xsl:call-template name="title">
                                    <xsl:with-param name="titleName" select="title"/>
                                </xsl:call-template>
                            </td>
                            <td>
                                <xsl:call-template name="artist">
                                    <xsl:with-param name="artistName" select="artist"/>
                                </xsl:call-template>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

    <!-- Дефинираме си съответните шаблони от условието -->
    <xsl:template name="year">
        <xsl:param name="yearName"/>
        <xsl:value-of select="$yearName"/>
    </xsl:template>

    <xsl:template name="title">
        <xsl:param name="titleName"/>
        <xsl:value-of select="$titleName"/>
    </xsl:template>

    <xsl:template name="artist">
        <xsl:param name="artistName"/>
        <xsl:value-of select="$artistName"/>
    </xsl:template>
</xsl:stylesheet>
