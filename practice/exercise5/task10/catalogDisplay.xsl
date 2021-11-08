<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <xsl:for-each select="//cd">
                    <h1>CD_<xsl:value-of select="position()"/></h1>
                    <table border="1px solid" cellpadding="20">
                        <thead>
                            <th>Title</th>
                            <th>Year</th>
                            <th>Tracklist</th>
                        </thead>
                        <tr>
                            <td><xsl:apply-templates select="title"/></td>
                            <td><xsl:apply-templates select="year"/></td>
                            <td>
                                <ol>
                                    <xsl:apply-templates select="tracklist/track"/>
                                </ol>
                            </td>
                        </tr>
                    </table>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>

    <!-- Неименувани шаблони, които ще извикваме в главния шаблон. -->

    <!-- Взима стойността на title и извиква шаблона, който изкарва атрибутите на елемента. -->
    <xsl:template match="title">
        <xsl:value-of select="."/>
        <xsl:call-template name="attributes"/>
    </xsl:template>
    
    <!-- Взима стойността на year и извиква шаблона, който изкарва атрибутите на елемента. -->
    <xsl:template match="year">
        <xsl:value-of select="."/>
        <xsl:call-template name="attributes"/>
    </xsl:template>

    <!-- Взима стойността на track и извиква шаблона, който изкарва атрибутите на елемента. -->
    <xsl:template match="track">
        <li>
            <xsl:value-of select="."/>
            <xsl:call-template name="attributes"/>
        </li>
    </xsl:template>

    <!-- Тъй като се повтаря изкарването на атрибутите, реших да го отделя в друг шаблон -->
    <xsl:template name="attributes">
        <ul>
            <xsl:for-each select="@*">
                <li><xsl:value-of select="name()"/> = <xsl:value-of select="."/></li>
            </xsl:for-each>
        </ul>
    </xsl:template>
</xsl:stylesheet>
