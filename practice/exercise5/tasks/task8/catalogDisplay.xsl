<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- Задаваме какъв изход искаме да получим (в случая xml) -->
    <xsl:output method="xml" version="1.0" encoding="UTF-8"/>

    <!-- За cd елемента създаваме йерархия като копираме поделементите title и tracklist -->
    <xsl:template match="//cd">
        <cd>
            <xsl:copy-of select="title"/>
            <xsl:copy-of select="tracklist"/>
        </cd>
    </xsl:template>
</xsl:stylesheet>
