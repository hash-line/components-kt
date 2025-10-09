enum class HashTextStyle{
    DisplayLarge,
    DisplayMedium,
    DisplaySmall,
    HeadlineLarge,
    HeadlineMedium,
    HeadlineSmall,
    TitleLarge,
    TitleMedium,
    TitleSmall,
    BodyLarge,
    BodyMedium,
    BodySmall,
    LabelLarge,
    LabelMedium,
    LabelSmall
}


enum class TextAlign{
    Start,
    Center,
    End,
    Justify
}

interface Text : Component {

    /**
     * The style of the text, which defines its appearance.
     */
    var style: HashTextStyle

    /**
     * The alignment of the text content.
     */
    var alignment: TextAlign
    
}

abstract class BaseText(
    id: String = "",
    enabled: Boolean = true,
    presence: Presence = Presence.Visible,
    override var style: HashTextStyle = HashTextStyle.BodyMedium,
    override var alignment: TextAlign = TextAlign.Start
) : BaseComponent(
    id = id,
    enabled = enabled,
    presence = presence
), Text

class ResText(
    id: String = "",
    style: HashTextStyle = HashTextStyle.BodyMedium,
    alignment: TextAlign = TextAlign.Start,
    val resId: Int,
) : BaseText(
    id = id,
    style = style,
    alignment = alignment
)


class PlainText(
    id: String = "",
    style: HashTextStyle = HashTextStyle.BodyMedium,
    alignment: TextAlign = TextAlign.Start,
    
    val text: String
) : BaseText(
    id = id,
    style = style,
    alignment = alignment,
    
)

class AnnotatedText(
    id: String = "",
    style: HashTextStyle = HashTextStyle.BodyMedium,
    alignment: TextAlign = TextAlign.Start,
    val textWithLink: TextWithLinks
) : BaseText(
    id = id,
    style = style,
    alignment = alignment,
    
) {

//    suspend fun select(link: Link) {
//        raiseEvent(LinkClicked(
//            link = link
//        ))
//    }
}

fun String.asPlainText(
    id: String = "",
    style: HashTextStyle = HashTextStyle.BodyMedium,
    alignment: TextAlign = TextAlign.Start
): PlainText = PlainText(
    id = id,
    text = this, style = style, alignment = alignment
)

/**
 * Data class representing a text that contains multiple clickable links.
 *
 * @property text The full string of text, including both regular text and parts where links will appear.
 * @property links A list of links that define the sections of the text which are clickable.
 */
class TextWithLinks(
    val text: String,
    val links: List<Link>
)

/**
 * Data class representing a clickable link within a block of text.
 *
 * @property startIndex The starting position of the link within the full text (inclusive).
 * @property endIndex The ending position of the link within the full text (exclusive).
 * @property url The URL that will be opened when the corresponding section of the text is clicked.
 */
class Link(
    val startIndex: Int,
    val endIndex: Int,
    val url: String
)