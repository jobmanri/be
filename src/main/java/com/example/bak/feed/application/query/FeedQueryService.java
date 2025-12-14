package com.example.bak.feed.application.query;

import com.example.bak.feed.application.query.dto.FeedDetail;
import com.example.bak.feed.application.query.dto.FeedSummary;
import com.example.bak.feed.application.query.port.FeedQueryPort;
import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedQueryService {

    private final FeedQueryPort feedQueryPort;

    public FeedDetail getFeedDetail(Long feedId) {
        return feedQueryPort.findDetailById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));
    }

    public FeedSummary getFeedSummary(Long feedId) {
        return feedQueryPort.findSummaryById(feedId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FEED_NOT_FOUND));
    }

    public List<FeedSummary> getFeeds(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FeedSummary> feedPage = feedQueryPort.findAll(pageable);
        return feedPage.getContent();
    }
}
