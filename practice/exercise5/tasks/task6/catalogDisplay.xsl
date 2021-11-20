<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head></head>
            <body>
                <table border="1px solid">
                    <!-- Заглавен ред на таблицата -->
                    <tr>
                        <th>Year</th>
                        <th>Title</th>
                        <th>Artist</th>
                    </tr>
                    <xsl:for-each select=".//cd">
                        <tr>
                            <!-- Извикваме темплейтите за поделементите на всяко cd съответно -->
                            <td><xsl:call-template name="year"/></td>
                            <td><xsl:call-template name="title"/></td>
                            <td><xsl:call-template name="artist"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

    <!-- Дефинираме си съответните шаблони от условието -->
    <xsl:template name="year">
        <xsl:value-of select="year"/>
    </xsl:template>

    <xsl:template name="title">
        <xsl:value-of select="title"/>
    </xsl:template>

    <xsl:template name="artist">
        <xsl:value-of select="artist"/>
    </xsl:template>
</xsl:stylesheet>
