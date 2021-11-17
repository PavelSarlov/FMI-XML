<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- Задаваме какъв изход искаме да получим (в случая xml) -->
    <xsl:output method="xml" version="1.0" encoding="UTF-8"/>

    <xsl:template match="/">
        <xsl:element name="newCatalog">
            <xsl:for-each select=".//cd">
                <!-- За всяко cd създаваме елемент със същото име, атрибути и поделементи, които са ни зададени в условието -->
                <xsl:element name="{.}">

                    <!-- Атрибутите на cd -->
                    <xsl:attribute name="category"><xsl:value-of select="@category"/></xsl:attribute>
                    <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>

                    <!-- Поделементите на cd, искани от условието -->
                    <xsl:element name="title">
                        <xsl:value-of select="title"/>
                    </xsl:element>

                    <xsl:element name="tracklist">
                        <xsl:attribute name="num"><xsl:value-of select="tracklist/@num"/></xsl:attribute>
                        <xsl:for-each select="tracklist/track">
                            <xsl:element name="track">
                                <xsl:attribute name="length"><xsl:value-of select="@length"/></xsl:attribute>
                                <xsl:value-of select="text()"/>
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
