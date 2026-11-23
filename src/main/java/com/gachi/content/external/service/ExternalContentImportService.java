package com.gachi.content.external.service;

import com.gachi.content.external.dto.ExternalContentImportResult;
import com.gachi.content.external.sportsdb.SportsDbClient;
import com.gachi.content.external.sportsdb.dto.SportsDbEventListResponse;
import com.gachi.content.external.tmdb.TmdbClient;
import com.gachi.content.external.tmdb.dto.TmdbMovieResponse;
import com.gachi.content.external.tmdb.dto.TmdbPageResponse;
import com.gachi.content.external.tmdb.dto.TmdbTvResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalContentImportService {

  private final TmdbClient tmdbClient;
  private final SportsDbClient sportsDbClient;
  private final ExternalContentService externalContentService;

  public ExternalContentImportResult importTmdbMovies(int page, String language) {
    TmdbPageResponse<TmdbMovieResponse> response = tmdbClient.fetchDiscoverMovies(page, language);
    return externalContentService.importTmdbMovies(emptyIfNull(response.results()));
  }

  public ExternalContentImportResult importTmdbTvSeries(int page, String language) {
    TmdbPageResponse<TmdbTvResponse> response = tmdbClient.fetchDiscoverTvSeries(page, language);
    return externalContentService.importTmdbTvSeries(emptyIfNull(response.results()));
  }

  public ExternalContentImportResult importSportsDbLeagueEvents(String leagueId) {
    SportsDbEventListResponse response = sportsDbClient.fetchNextLeagueEvents(leagueId);
    return externalContentService.importSportsEvents(emptyIfNull(response.events()));
  }

  public ExternalContentImportResult importSportsDbDayEvents(
      LocalDate date,
      String sport,
      String leagueId
  ) {
    SportsDbEventListResponse response = sportsDbClient.fetchEventsByDay(date, sport, leagueId);
    return externalContentService.importSportsEvents(emptyIfNull(response.events()));
  }

  private <T> List<T> emptyIfNull(List<T> values) {
    return values == null ? List.of() : values;
  }
}
