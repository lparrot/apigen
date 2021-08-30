package fr.lauparr.apigen.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class DaoUtils {

  private static final String SPLIT_CHAR = "\\.";

  /**
   * Constructeur
   */
  private DaoUtils() {
  }

  public static <T> T convertToDto(final Object data, final Class<T> clazz) {
    return Objects.requireNonNull(SpringUtils.getBean(ProjectionFactory.class)).createProjection(clazz, data);
  }

  public static <T> List<T> convertListDto(final List<?> liste, final Class<T> clazz) {
    return liste.stream().map(x -> DaoUtils.convertToDto(x, clazz)).collect(Collectors.toList());
  }

  public static <T> Page<T> convertPageDto(final Page<?> page, final Class<T> clazz) {
    return page.map(x -> DaoUtils.convertToDto(x, clazz));
  }

  public static <T, ID> T findRandom(final PagingAndSortingRepository<T, ID> repository) {
    final long count = repository.count();
    final int idx = new SecureRandom().nextInt((int) count);
    final List<T> result = repository.findAll(PageRequest.of(idx, 1)).getContent();
    if (!result.isEmpty()) {
      return result.get(0);
    }
    return null;
  }

}
