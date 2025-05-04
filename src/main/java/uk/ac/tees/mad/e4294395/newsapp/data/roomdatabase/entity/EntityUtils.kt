package uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.entity

import uk.ac.tees.mad.e4294395.newsapp.data.model.Article
import uk.ac.tees.mad.e4294395.newsapp.data.model.Source

fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        source = source.toSourceEntity()
    )
}

fun Source.toSourceEntity(): SourceEntity {
    return SourceEntity(
        id = id,
        name = name
    )
}

