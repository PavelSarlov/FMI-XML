(: 1) :)
    (: 1.1) Въртим по всеки track на определения tracklist и връщаме стойността му. :)
    for $track in //tracklist[@num=1]/track
    return $track/text()

    (: 1.2) Сортираме по text(). :)
    for $track in //tracklist[@num=1]/track
    order by $track/text()
    return $track/text()

    (: 1.3) Аналогично на 2). :)
    for $track in //tracklist[@num=1]/track
    order by $track/text() descending
    return $track/text()

(: 2) Въртим по cd-тата и достъпваме поделементите им.  :)
element records {
    for $cd in //cd
    return
        element record {
            attribute cd_ID { $cd/@id },
            attribute artist { $cd/artist/text() },
            element info {
    	        text { concat("Title: ", $cd/title/text()), ", Year: ", $cd/year/text(), ", Track numbers: ", count($cd//track) }
            }
        }
}

(: По-лесен начин. :)
<records>
{
    for $cd in //cd
    return
    <record cd_ID="{$cd/@id}" artist="{$cd/artist/text()}">
    	<info>Title: {$cd/title/text()}, Year: {$cd/year/text()}, Track numbers: {count($cd//track)}</info>
    </record>
}
</records>

(: 3) Аналогично на 2) :)
<tracks>
{
    for $track in //track
    return <track>{$track/text()}</track>
}
</tracks>

