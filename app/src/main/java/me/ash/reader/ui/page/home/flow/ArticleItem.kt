package me.ash.reader.ui.page.home.flow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.size.Scale
import me.ash.reader.R
import me.ash.reader.data.entity.ArticleWithFeed
import me.ash.reader.data.preference.*
import me.ash.reader.ui.component.AsyncImage
import me.ash.reader.ui.ext.formatAsString

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    articleWithFeed: ArticleWithFeed,
    onClick: (ArticleWithFeed) -> Unit = {},
) {
    val context = LocalContext.current
    val articleListFeedIcon = LocalFlowArticleListFeedIcon.current
    val articleListFeedName = LocalFlowArticleListFeedName.current
    val articleListImage = LocalFlowArticleListImage.current
    val articleListDesc = LocalFlowArticleListDesc.current
    val articleListDate = LocalFlowArticleListTime.current

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick(articleWithFeed) }
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .alpha(if (articleWithFeed.article.isStarred || articleWithFeed.article.isUnread) 1f else 0.5f),
    ) {
        // Upper
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Feed name
            if (articleListFeedName.value) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = if (articleListFeedIcon.value) 30.dp else 0.dp),
                    text = articleWithFeed.feed.name,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            // Right
            if (articleListDate.value) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (!articleListFeedName.value) {
                        Spacer(Modifier.width(if (articleListFeedIcon.value) 30.dp else 0.dp))
                    }
                    // Starred
                    if (articleWithFeed.article.isStarred) {
                        Icon(
                            modifier = Modifier
                                .size(14.dp)
                                .padding(end = 2.dp),
                            imageVector = Icons.Rounded.Star,
                            contentDescription = stringResource(R.string.starred),
                            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
                        )
                    }

                    // Date
                    Text(
                        text = articleWithFeed.article.date.formatAsString(
                            context,
                            onlyHourMinute = true
                        ),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }

        // Lower
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Feed icon
            if (articleListFeedIcon.value) {
                Row(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                ) {}
                Spacer(modifier = Modifier.width(10.dp))
            }

            // Article
            Column(
                modifier = Modifier.weight(1f),
            ) {

                // Title
                Text(
                    text = articleWithFeed.article.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = if (articleListDesc.value) 2 else 4,
                    overflow = TextOverflow.Ellipsis,
                )

                // Description
                if (articleListDesc.value && articleWithFeed.article.shortDescription.isNotBlank()) {
                    Text(
                        text = articleWithFeed.article.shortDescription,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            // Image
            if (articleWithFeed.article.img != null && articleListImage.value) {
                AsyncImage(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    data = articleWithFeed.article.img,
                    scale = Scale.FILL,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}