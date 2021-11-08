<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head></head>
            <body>
                <h1>Константа</h1>
                <xsl:variable name="var" select="'Константа или променлива ?'"/>
                <xsl:value-of select="$var"/>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
