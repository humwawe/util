package template.tree;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;

/**
 * @author hum
 */
public class ChthollyTree {

  // 区间操作，使用TreeSet来进行存储和删除
  TreeSet<Range> set1 = new TreeSet<>(Comparator.comparingInt(x -> x.l));
  TreeSet<Range> set2 = new TreeSet<>((x, y) -> {
    int d = x.len - y.len;
    if (d == 0) {
      return x.l - y.l;
    }
    return d;
  });

  // 每次与floor进行split
  // 取出idx，则使用split(idx),split(idx+1)
  private void split(int idx) {
    Range floor = set1.floor(new Range(idx, idx));
    if (floor.l == idx || idx > floor.r) {
      return;
    }
    set1.remove(floor);
    set2.remove(floor);
    set1.add(new Range(floor.l, idx - 1));
    set2.add(new Range(floor.l, idx - 1));
    set1.add(new Range(idx, floor.r));
    set2.add(new Range(idx, floor.r));
  }

  // 每次与lower进行合并
  // 考虑合并左右两个区间可以使用
  // union((idx);
  // union(idx+1) 保证idx+1合法，否则floor lower为同一个区间值
  private void union(int idx) {
    Range tmp = new Range(idx, idx);
    Range floor = set1.floor(tmp);
    Range lower = set1.lower(tmp);
    if (lower == null) {
      return;
    }
    set1.remove(floor);
    set2.remove(floor);
    set1.remove(lower);
    set2.remove(lower);
    set1.add(new Range(lower.l, floor.r));
    set2.add(new Range(lower.l, floor.r));
  }

  class Range {
    int l;
    int r;
    int len;

    // 左闭右闭区间
    public Range(int l, int r) {
      this.l = l;
      this.r = r;
      this.len = r - l + 1;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Range)) {
        return false;
      }
      Range range = (Range) o;
      return l == range.l && r == range.r && len == range.len;
    }

    @Override
    public int hashCode() {
      return Objects.hash(l, r, len);
    }
  }
}
