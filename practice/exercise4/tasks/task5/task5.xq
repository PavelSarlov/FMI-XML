(: 1) :)

    (: 
    Функцията приема catalog елемент и изкарва стойността на track елементите под него. Приема и един друг аргумент - order - от тип низ,
    приемащ стойности "asc" за нарастващо сортиране, "desc" за низходящ и какъвто и да е друг за без ред.
    :)
    declare function local:getTracksText($cat as element(), $order as string) 
    as text()* {
        switch($order)
            case "desc" return (
                for $track in $cat//tracklist[@num=1]/track
                order by $track/text() descending
                return $track/text()
                )
            case "asc" return (
                for $track in $cat//tracklist[@num=1]/track
                order by $track/text()
                return $track/text() 
                )
            default return (
                for $track in $cat//tracklist[@num=1]/track
                return $track/text()
                )
    };

    (: 1.1) Въртим по всеки track на определения tracklist и връщаме стойността му. :)
    local:getTracksText(/catalog, "")
    
    (: 1.2) Сортираме по text(). :)
    local:getTracksText(/catalog, "asc")

    (: 1.3) Аналогично на 2). :)
    local:getTracksText(/catalog, "desc")

(: 2) Въртим по cd-тата и достъпваме поделементите им. :)
declare function local:getRecords($cat as element()) 
as element()* {
    for $cd in $cat//cd
    return
        element record {
            attribute cd_ID { $cd/@id },
            attribute artist { $cd/artist/text() },
            element info {
    	        text { concat("Title: ", $cd/title/text()), ", Year: ", $cd/year/text(), ", Track numbers: ", count($cd//track) }
            }
        }
};

element records {
    local:getRecords(/catalog)
}

(: 3) Аналогично на 2) :)
declare function local:getTracks($cat)
as element()* {
    for $track in $cat//track
    return <track>{$track/text()}</track>
};

<tracks>
{
    local:getTracks(/catalog)
}
</tracks>

