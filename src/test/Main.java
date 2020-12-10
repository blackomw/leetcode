package test;

import java.util.*;

public class Main {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int x) {
            val = x;
        }

        ListNode(int x, ListNode next) {
            val = x;
            this.next = next;
        }
    }

    static class Solution {

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode ret = null;
            ListNode n = null;
            while (l1 != null || l2 != null) {
                int cur = n != null && n.next != null ? 1 : 0;
                if (l1 != null) {
                    cur += l1.val;
                    l1 = l1.next;
                }
                if (l2 != null) {
                    cur += l2.val;
                    l2 = l2.next;
                }
                if (n == null) {
                    ret = n = new ListNode(cur % 10);
                } else {
                    if (n.next == null)
                        n.next = new ListNode(cur % 10);
                    else
                        n.next.val = cur % 10;
                    n = n.next;
                }
                if (cur >= 10) {
                    n.next = new ListNode(1);
                }
            }
            return ret;
        }

        public int clen(String s) {
            int ret = 0;
            int tLen = 0;
            HashMap<Character, Integer> map = new HashMap<>();
            int i = 0, l = s.length();
            for (; i < l; ++i) {
                Integer v;
                if ((v = map.put(s.charAt(i), i)) == null) {
                    ++tLen;
                    if (tLen > ret)
                        ret = tLen;
                } else {
                    if (tLen > ret)
                        ret = tLen;
                    i = v;
                    tLen = 0;
                    map.clear();
                }
            }
            return ret;
        }

        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int len1 = nums1.length, len2 = nums2.length;
            int[] nums = new int[len1 + len2];
            int k = 0;
            for (int i = 0, j = 0; i < len1 || j < len2; ) {
                if (i >= len1) {
                    nums[k++] = nums2[j];
                    ++j;
                } else if (j >= len2) {
                    nums[k++] = nums1[i];
                    ++i;
                } else {
                    int n1 = nums1[i];
                    int n2 = nums2[j];
                    if (n1 <= n2) {
                        nums[k++] = n1;
                        ++i;
                    } else {
                        nums[k++] = n2;
                        ++j;
                    }
                }
            }

            float ret;
            if (nums.length % 2 == 0) {
                int k1 = nums.length / 2;
                int k2 = k1 - 1;
                ret = (nums[k1] + nums[k2]) / 2.0f;
            } else {
                ret = nums[nums.length / 2];
            }
            return ret;
        }

        public String longestPalindrome(String s) {
            if (s == null || s.isEmpty()) return "";
            int len = s.length();
            for (int i = len; i > 0; --i) {
                for (int j = 0, size = len - i; j <= size; ++j) {
                    if (isHuiwen(s, j, j + i - 1)) {
                        return s.substring(j, j + i);
                    }
                }
            }
            return s.substring(0, 1);
        }

        public boolean isHuiwen(String str, int start, int end) {
            for (int i = start, size = start + (end - start + 1) / 2; i < size; ++i) {
                if (str.charAt(i) != str.charAt(end - (i - start)))
                    return false;
            }
            return true;
        }

        public String convert(String s, int numRows) {
            if (numRows < 2) return s;
            String ret = "";
            int len = s.length();
            int n0 = numRows - 2;
            int n1 = numRows + n0;
            for (int i = 0; i < numRows; ++i) {
                int c = i;
                if (i == 0 || i == numRows - 1) {
                    while (c < len) {
                        ret += s.charAt(c);
                        c += n1;
                    }
                } else {
                    while (c < len) {
                        ret += s.charAt(c);
                        int b = numRows - i - 1;
                        int d = c + b + b;
                        if (d < len)
                            ret += s.charAt(d);
                        c += n1;
                    }
                }
            }
            return ret;
        }

        public int reverse(int x) {
            int ret = 0;
            int v = x;
            while (v != 0) {
                int rt = ret * 10;
                if (rt / 10 != ret) {
                    return 0;
                }
                int t = v % 10;
                ret = rt + t;
                if (ret - t != rt) {
                    return 0;
                }
                v = v / 10;
            }
            return ret;
        }

        public int myAtoi(String str) {
            int ret = 0;
            boolean ne = false;
            boolean started = false;
            for (int i = 0, len = str.length(); i < len; ++i) {
                char c = str.charAt(i);
                boolean isNum = (c >= '0' && c <= '9');
                if (started && !isNum) {
                    break;
                }
                if (c == '+' || c == '-' || isNum) {
                    started = true;
                    if (c == '-') {
                        ne = true;
                    }
                    if (!isNum) {
                        continue;
                    }
                }
                if (!started) {
                    if (c == ' ') {
                        continue;
                    }
                    break;
                }

                int t = c - '0';
                if (!ne && ret > (Integer.MAX_VALUE - t) / 10)
                    return Integer.MAX_VALUE;
                else if (ne && -ret < (Integer.MIN_VALUE + t) / 10)
                    return Integer.MIN_VALUE;
                ret = ret * 10 + t;
            }
            return ne ? -ret : ret;
        }

        public boolean isMatch(String s, String p) {
            int sLen = s != null ? s.length() : 0;
            int pLen = p != null ? p.length() : 0;
            if (pLen == 0) {
                return sLen == 0;
            }
            if (sLen == 0) {
                if (pLen % 2 == 0) {
                    for (int i = 1; i < pLen; i += 2) {
                        if (p.charAt(i) != '*') {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }

            boolean flag0 = s.charAt(0) == p.charAt(0) || p.charAt(0) == '.';
            boolean star = pLen > 1 && p.charAt(1) == '*';
            if (star) {
                if (flag0) {
                    return isMatch(s, p.substring(2)) || isMatch(s.substring(1), p);
                } else {
                    return isMatch(s, p.substring(2));
                }
            } else {
                if (flag0) {
                    return isMatch(s.substring(1), p.substring(1));
                } else {
                    return false;
                }
            }
        }

        public int maxArea(int[] height) {
            int len = height != null ? height.length : 0;
            if (len < 2) return 0;
            int max = 0;
            for (int i = 0; i < len; ++i) {
                int hi = height[i];
                for (int j = i + 1; j < len; ++j) {
                    int hj = height[j];
                    int t = (j - i) * (hi > hj ? hj : hi);
                    if (t > max) {
                        max = t;
                    }
                }
            }
            return max;
        }

        static HashMap<Integer, String> romans = new HashMap<>(30);

        static {
            romans.put(1, "I");
            romans.put(2, "II");
            romans.put(3, "III");
            romans.put(4, "IV");
            romans.put(5, "V");
            romans.put(6, "VI");
            romans.put(7, "VII");
            romans.put(8, "VIII");
            romans.put(9, "IX");

            romans.put(10, "X");
            romans.put(20, "XX");
            romans.put(30, "XXX");
            romans.put(40, "XL");
            romans.put(50, "L");
            romans.put(60, "LX");
            romans.put(70, "LXX");
            romans.put(80, "LXXX");
            romans.put(90, "XC");

            romans.put(100, "C");
            romans.put(200, "CC");
            romans.put(300, "CCC");
            romans.put(400, "CD");
            romans.put(500, "D");
            romans.put(600, "DC");
            romans.put(700, "DCC");
            romans.put(800, "DCCC");
            romans.put(900, "CM");

            romans.put(1000, "M");
            romans.put(2000, "MM");
            romans.put(3000, "MMM");
        }

        public String intToRoman(int num) {
            HashMap<Integer, String> map = romans;
            String ret = "";
            int tmp = num;
            int i = 1;
            while (tmp > 0) {
                int n = tmp % 10;
                String t = romans.get(n * i);
                if (t != null) {
                    ret = t + ret;
                }
                i = i * 10;
                tmp = tmp / 10;
            }
            return ret;
        }

        static HashMap<Character, Integer> romans2 = new HashMap<>(7);

        static {
            romans2.put('I', 1);
            romans2.put('V', 5);
            romans2.put('X', 10);
            romans2.put('L', 50);
            romans2.put('C', 100);
            romans2.put('D', 500);
            romans2.put('M', 1000);
        }

        public int romanToInt(String s) {
            int ret = 0;
            int pre = 0;
            for (int len = s.length(), i = len - 1; i >= 0; --i) {
                Integer r = romans2.get(s.charAt(i));
                if (r < pre)
                    ret -= r;
                else
                    ret += r;
                pre = r;
            }
            return ret;
        }

        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) return "";
            if (strs.length == 1) return strs[0];

            int len = Integer.MAX_VALUE;
            for (int i = 0, sLen = strs.length; i < sLen; ++i) {
                int t = strs[i].length();
                if (t < len) {
                    len = t;
                }
            }
            StringBuilder sb = new StringBuilder();
            int count = strs.length;
            char c = 0;
            outer:
            for (int i = 0; i < len; ++i) {
                c = strs[0].charAt(i);
                for (int j = 1; j < count; ++j) {
                    if (strs[j].charAt(i) != c) {
                        break outer;
                    }
                }
                sb.append(c);
            }
            return sb.toString();
        }

        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> ret = new ArrayList<>();
            int len = nums != null ? nums.length : 0;
            if (len < 3) return ret;

            int zeroCount = 0;
            int neEndIdx = -1;
            int poStartIdx = -1;
            Arrays.sort(nums);
            for (int i = 0, s = nums.length; i < s; ++i) {
                if (nums[i] == 0) ++zeroCount;
                else if (nums[i] < 0) neEndIdx = i;
                else {
                    if (poStartIdx < 0) poStartIdx = i;
                    else break;
                }
            }

            if (zeroCount > 0) {
                if (zeroCount > 2) {
                    ret.add(Arrays.asList(0, 0, 0));
                }
                if (neEndIdx >= 0 && poStartIdx >= 0) {
                    int last = 0;
                    for (int i = neEndIdx, j = poStartIdx; i >= 0 && j < len; ) {
                        int n = nums[i];
                        int p = nums[j];
                        if (-n == p) {
                            if (last != p) {
                                ret.add(Arrays.asList(n, 0, p));
                            }
                            last = p;
                            --i;
                            ++j;
                        } else if (-n < p) {
                            --i;
                        } else {
                            ++j;
                        }
                    }
                }
            }
            if (neEndIdx >= 0 && poStartIdx >= 0) {
                int last = 0;
                for (int i = 0; i <= neEndIdx; ++i) {
                    int n = nums[i];
                    if (n == last) {
                        continue;
                    }
                    last = n;
                    int pLast = 0;
                    for (int j = poStartIdx; j < len; ++j) {
                        int p = nums[j];
                        if (pLast == p) {
                            continue;
                        }
                        pLast = p;
                        if (-n < p * 2) {
                            break;
                        }
                        int pLast1 = 0;
                        for (int k = j + 1; k < len; ++k) {
                            int p1 = nums[k];
                            if (pLast1 == p1) {
                                continue;
                            }
                            pLast1 = p1;
                            if (-n < p + p1) {
                                break;
                            } else if (-n == p + p1) {
                                ret.add(Arrays.asList(n, p, p1));
                            }
                        }
                    }
                }
                last = 0;
                for (int i = poStartIdx; i < len; ++i) {
                    int p = nums[i];
                    if (p == last) {
                        continue;
                    }
                    last = p;
                    int nLast = 0;
                    for (int j = neEndIdx; j >= 0; --j) {
                        int n = nums[j];
                        if (n == nLast) {
                            continue;
                        }
                        nLast = n;
                        if (p < -n * 2) {
                            break;
                        }
                        int nLast1 = 0;
                        for (int k = j - 1; k >= 0; --k) {
                            int n1 = nums[k];
                            if (nLast1 == n1) {
                                continue;
                            }
                            nLast1 = n1;
                            if (p < -n - n1) {
                                break;
                            } else if (p == -n - n1) {
                                ret.add(Arrays.asList(n1, n, p));
                            }
                        }
                    }
                }
            }
            return ret;
        }

        public int threeSumClosest(int[] nums, int target) {
            int ret = Integer.MAX_VALUE;
            Arrays.sort(nums);
            int min = Integer.MAX_VALUE;
            int len = nums.length;
            for (int i = 0; i <= len - 3; ++i) {
                int v = nums[i];
                for (int l = i + 1, r = len - 1; l < r; ) {
                    int t = v + nums[l] + nums[r];
                    int delta = t - target;
                    int pt = Math.abs(delta);
                    if (pt < min) {
                        min = pt;
                        ret = t;
                        if (pt == 0) {
                            return ret;
                        }
                    }
                    if (delta > 0) {
                        --r;
                    } else {
                        ++l;
                    }
                }
            }
            return ret;
        }

        static List<String[]> dtl = new ArrayList<>(8);

        static {
            dtl.add(new String[]{"a", "b", "c"});
            dtl.add(new String[]{"d", "e", "f"});
            dtl.add(new String[]{"g", "h", "i"});
            dtl.add(new String[]{"j", "k", "l"});
            dtl.add(new String[]{"m", "n", "o"});
            dtl.add(new String[]{"p", "q", "r", "s"});
            dtl.add(new String[]{"t", "u", "v"});
            dtl.add(new String[]{"w", "x", "y", "z"});
        }

        public List<String> letterCombinations(String digits) {
            int len = digits != null ? digits.length() : 0;
            if (len == 0) {
                return new ArrayList<>();
            }
            List<String> ret = new ArrayList<>();
            String[] c0 = dtl.get(digits.charAt(0) - '2');
            int c0Len = c0.length;
            List<String> subs = letterCombinations(digits.substring(1));
            int sLen = subs.size();
            if (sLen > 0) {
                for (int i = 0; i < sLen; ++i) {
                    String s = subs.get(i);
                    for (int j = 0; j < c0Len; ++j) {
                        ret.add(c0[j] + s);
                    }
                }
            } else {
                for (int j = 0; j < c0Len; ++j) {
                    ret.add(c0[j]);
                }
            }
            return ret;
        }

        public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> ret = new ArrayList<>();
            int len = nums != null ? nums.length : 0;
            if (len < 4) {
                return ret;
            }
            Arrays.sort(nums);
            HashSet<String> set = new HashSet<>();
            for (int i0 = 0; i0 < len - 3; ++i0) {
                int t0 = nums[i0];
                for (int i1 = i0 + 1; i1 < len - 2; ++i1) {
                    int t1 = nums[i1];
                    int t01 = t0 + t1;
                    if (t1 > 0 && t01 > target) {
                        break;
                    }
                    for (int j = i1 + 1, k = len - 1; j < k; ) {
                        int t2 = nums[j];
                        int t3 = nums[k];
                        int t0123 = t01 + t2 + t3;
                        if (t0123 == target) {
                            List<Integer> lst = Arrays.asList(t0, t1, t2, t3);
                            if (set.add(lst.toString()))
                                ret.add(lst);
                            ++j;
                            --k;
                        } else if (t0123 > target) {
                            --k;
                        } else {
                            ++j;
                        }
                    }
                }
            }
            return ret;
        }

        public ListNode removeNthFromEnd(ListNode head, int n) { // 19
            ArrayList<ListNode> ns = new ArrayList<>();
            ListNode t = head;
            while (t != null) {
                ns.add(t);
                t = t.next;
            }
            int r = ns.size() - n;
            int p = r - 1;
            ListNode rmNode = ns.get(r);
            ListNode preNode = p < 0 ? null : ns.get(p);
            if (preNode != null) {
                preNode.next = rmNode.next;
            } else {
                head = rmNode.next;
            }
            return head;
        }

        public boolean isValid(String s) { // 20
            if (s == null || s.isEmpty())
                return true;
            ArrayList<Character> stack = new ArrayList<>(s.length());
            for (int i = 0, len = s.length(); i < len; ++i) {
                char c = s.charAt(i);
                if (c == '(' || c == '{' || c == '[') {
                    stack.add(c);
                } else {
                    char last = stack.isEmpty() ? 0 : stack.get(stack.size() - 1);
                    if (last == 0 || (last == '(' && c != ')') || (last == '[' && c != ']') || (last == '{' && c != '}'))
                        return false;
                    stack.remove(stack.size() - 1);
                }
            }
            return stack.isEmpty();
        }

        public ListNode mergeTwoLists(ListNode l1, ListNode l2) { // 21
            ListNode tmp = null, ret = null;
            while (l1 != null || l2 != null) {
                int v;
                if (l2 == null || (l1 != null && l1.val < l2.val)) {
                    v = l1.val;
                    l1 = l1.next;
                } else {
                    v = l2.val;
                    l2 = l2.next;
                }
                if (ret == null) {
                    ret = new ListNode();
                    ret.val = v;
                    tmp = ret;
                } else {
                    tmp.next = new ListNode(v);
                    tmp = tmp.next;
                }
            }
            return ret;
        }

        public List<String> generateParenthesis(int n) { // 22
            ArrayList<String> ret = new ArrayList<>();
            final int size = n + n;
            int l = n;
            int sum = 0, lastLeftIdx = n;
            char[] cs = new char[size];
            for (int i = 0; i < size; ++i) {
                if (l > 0) {
                    cs[i] = '(';
                    --l;
                    ++sum;
                    lastLeftIdx = i;
                } else {
                    cs[i] = ')';
                    --sum;
                    boolean backtrace = false;
                    if (sum == 0 && i == size - 1) {
                        ret.add(String.valueOf(cs));
                        backtrace = true;
                    } else if (sum < 0) {
                        backtrace = true;
                    }
                    if (backtrace) {
                        do {
                            if (lastLeftIdx == 0) {
                                return ret;
                            }
                            sum = 0;
                            l = n;
                            int newLastLeftIdx = lastLeftIdx;
                            for (int j = 0; j < lastLeftIdx; ++j) {
                                if (cs[j] == '(') {
                                    --l;
                                    ++sum;
                                    newLastLeftIdx = j;
                                } else {
                                    --sum;
                                }
                            }
                            cs[i = lastLeftIdx] = ')';
                            --sum;
                            lastLeftIdx = newLastLeftIdx;
                        } while (sum < 0);
                    }
                }
            }
            return ret;
        }

        public ListNode mergeKLists(ListNode[] lists) { // 23
            ListNode ret = null, cur = null;
            while (true) {
                boolean hasData = false;
                int min = Integer.MAX_VALUE;
                for (int i = 0, len = lists.length; i < len; ++i) {
                    ListNode node = lists[i];
                    if (node != null) {
                        hasData = true;
                        int v = node.val;
                        if (v <= min)
                            min = v;
                    }
                }
                if (!hasData)
                    break;
                for (int i = 0, len = lists.length; i < len; ++i) {
                    ListNode node = lists[i];
                    if (node != null) {
                        if (node.val == min) {
                            lists[i] = node.next;
                            if (ret == null) {
                                ret = cur = new ListNode(min);
                            } else {
                                cur.next = new ListNode(min);
                                cur = cur.next;
                            }
                        }
                    }
                }
            }
            return ret;
        }

        public ListNode swapPairs(ListNode head) { // 24
            ArrayList<ListNode> nodes = new ArrayList<>();
            int count = -1;
            while (head != null) {
                if ((++count) % 2 == 0) {
                    nodes.add(count, null);
                    nodes.add(count + 1, head);
                } else {
                    nodes.set(count - 1, head);
                }
                head = head.next;
            }

            ListNode ret = null;
            ListNode next = null;
            for (int i = nodes.size() - 1; i >= 0; --i) {
                ListNode node = nodes.get(i);
                if (node == null)
                    continue;
                ret = node;
                node.next = next;
                next = node;
            }
            return ret;
        }

        public ListNode reverseKGroup(ListNode head, int k) { // 25
            final ArrayList<ListNode> kList = new ArrayList<>(k);
            ListNode ret = null;
            int count = 0;
            ListNode pre = null;
            while (head != null) {
                kList.add(head);
                ListNode next = head.next;
                head = head.next;
                if (++count == k) {
                    for (int i = count - 1; i >= 0; --i) {
                        ListNode node = kList.get(i);
                        if (ret == null)
                            ret = node;
                        node.next = i == 0 ? next : kList.get(i - 1);
                    }
                    if (pre != null)
                        pre.next = kList.get(count - 1);
                    pre = kList.get(0);
                    count = 0;
                    kList.clear();
                }
            }
            return ret;
        }

        public int removeDuplicates(int[] nums) { // 26
            int p = 0;
            for (int i = 0, len = nums.length; i < len; ++i) {
                if (nums[p] != nums[i])
                    nums[++p] = nums[i];
            }
            return p + 1;
        }

        public int removeElement(int[] nums, int val) { // 27
            int p = 0;
            for (int i = 0, len = nums.length; i < len; ++i) {
                if (nums[i] != val)
                    nums[p++] = nums[i];
            }
            return p;
        }

        public int strStr(String haystack, String needle) { // 28
            if (haystack == null || needle == null)
                return -1;
            if (needle.isEmpty())
                return 0;

            int len1 = needle.length();
            outer:
            for (int i = 0, len0 = haystack.length(); i < len0; ++i) {
                if (haystack.charAt(i) == needle.charAt(0)) {
                    for (int j = 1; j < len1; ++j) {
                        if (i + j >= len0)
                            return -1;
                        if (haystack.charAt(i + j) != needle.charAt(j)) {
                            continue outer;
                        }
                    }
                    return i;
                }
            }
            return -1;
        }

        public int divide(int dividend, int divisor) { // 29
            if (divisor == 1)
                return dividend;
            else if (divisor == -1) {
                return dividend == Integer.MIN_VALUE ? Integer.MAX_VALUE : -dividend;
            }
            int ret = 0;
            int flag = 1;
            if (dividend < 0) {
                flag = -flag;
            } else {
                dividend = -dividend;
            }
            if (divisor < 0) {
                flag = -flag;
            } else {
                divisor = -divisor;
            }
            int r = dividend;
            while (r <= divisor) {
                r = r - divisor;
                ++ret;
            }
            return flag < 0 ? -ret : ret;
        }

        public List<Integer> findSubstring(String s, String[] words) { // 30
            if (s == null || words == null || s.isEmpty() || words.length == 0)
                return null;
            ArrayList<Integer> ret = new ArrayList<>();
            final int unitLen = words[0].length();
            final int wLen = words.length;
            final int len = s.length();
            for (int m = 0, mLen = len - wLen * unitLen + 1; m < mLen; ++m) {
                int tLen = wLen;
                for (int i = m; i < len - unitLen + 1; ) {
                    String ts = s.substring(i, i + unitLen);
                    int idx = -1;
                    for (int j = 0; j < tLen; ++j) {
                        if (ts.equals(words[j])) {
                            idx = j;
                            break;
                        }
                    }
                    if (idx < 0) {
                        break;
                    } else {
                        if (tLen == 1) {
                            int r = i - (wLen - 1) * unitLen;
                            if (!ret.contains(r))
                                ret.add(r);
                            break;
                        } else {
                            --tLen;
                            String tmp = words[idx];
                            words[idx] = words[tLen];
                            words[tLen] = tmp;
                            i += unitLen;
                        }
                    }
                }
            }
            return ret;
        }

        public void nextPermutation(int[] nums) { // 31
            final int len;
            if (nums == null || (len = nums.length) == 1)
                return;
            int m = 0;
            for (int i = len - 1; i > 0; --i) {
                int n1 = nums[i - 1];
                if (n1 < nums[i]) {
                    for (int j = len - 1; j >= i; --j) {
                        if (nums[j] > n1) {
                            nums[i - 1] = nums[j];
                            nums[j] = n1;
                            break;
                        }
                    }
                    m = i;
                    break;
                }
            }
            int c = (len - m) / 2;
            for (int j = 0; j < c; ++j) {
                int k0 = m + j, k1 = len - j - 1;
                int t = nums[k0];
                nums[k0] = nums[k1];
                nums[k1] = t;
            }
        }

        public int longestValidParentheses(String s) { // 32
            final int len;
            if (s == null || (len = s.length()) == 0)
                return 0;
            int max = 0;
            final ArrayList<Integer> res = new ArrayList<>();
            final ArrayList<Character> stack = new ArrayList<>();
            for (int i = 0; i < len; ++i) {
                char c = s.charAt(i);
                int ssize = stack.size();
                if (ssize > 0 && stack.get(ssize - 1) == '(' && c == ')') {
                    stack.remove(ssize - 1);

                    if (res.size() % 2 != 0) {
                        res.add(1);
                    } else {
                        res.set(res.size() - 1, res.get(res.size() - 1) + 1);
                    }

                    int obIdx = res.size() - 2;
                    int obValue = res.get(obIdx) - 1;
                    res.set(obIdx, obValue);
                    if (obValue == 0 && obIdx > 0) {
                        res.set(obIdx - 1, res.get(obIdx - 1) + res.get(obIdx + 1));
                        res.remove(obIdx + 1);
                        res.remove(obIdx);
                    }

                    int last = res.get(res.size() - 1);
                    if (last > max)
                        max = last;
                } else {
                    stack.add(c);

                    if (res.size() % 2 != 0) {
                        res.set(res.size() - 1, res.get(res.size() - 1) + 1);
                    } else {
                        res.add(1);
                    }
                }
            }
            return max * 2;
        }

        public int longestValidParentheses2(String s) { // 32
            final int len;
            if (s == null || (len = s.length()) == 0)
                return 0;
            final ArrayList<Integer> stack = new ArrayList<>(len);
            for (int i = 0; i < len; ++i) {
                char c = s.charAt(i);
                int ssize = stack.size();
                if (ssize > 0 && stack.get(ssize - 1) < 0 && c == ')') {
                    stack.remove(ssize - 1);
                } else {
                    stack.add(c == '(' ? -(i + 1) : i + 1);
                }
            }
            int max = 0;
            stack.add(len);
            int ssize = stack.size();
            int pre = 0;
            for (int i = 0; i < ssize; ++i) {
                int c = stack.get(i);
                if (c < 0)
                    c = -c;
                int m = c - pre;
                if (i != ssize - 1)
                    m -= 1;
                if (m > max)
                    max = m;
                pre = c;
            }
            return max;
        }

        public int search(int[] nums, int target) { // 33
            final int len;
            if (nums == null || (len = nums.length) == 0)
                return -1;

            int maxIdx = 0;
            int low = 0, high = len - 1;
            while (low <= high) {
                int midIdx = (low + high) / 2;
                int midVal = nums[midIdx];
                if (midIdx == low) {
                    maxIdx = midVal > nums[high] ? midIdx : high;
                    break;
                } else if (midIdx == high) {
                    maxIdx = midVal > nums[low] ? midIdx : low;
                    break;
                }
                int lv = nums[midIdx - 1];
                int rv = nums[midIdx + 1];
                if (lv < midVal && midVal < rv) {
                    if (midVal > nums[0]) {
                        low = midIdx;
                    } else {
                        high = midIdx;
                    }
                } else if (lv < midVal && midVal > rv) {
                    maxIdx = midIdx;
                    break;
                } else if (lv > midVal && midVal < rv) {
                    maxIdx = midIdx - 1;
                    break;
                }
            }

            if (target == nums[0]) {
                return 0;
            } else if (target == nums[len - 1]) {
                return len - 1;
            } else if (target == nums[maxIdx]) {
                return maxIdx;
            } else if (target > nums[maxIdx]) {
                return -1;
            } else if (target < nums[maxIdx == len - 1 ? 0 : maxIdx + 1]) {
                return -1;
            }

            if (target > nums[0]) {
                low = 0;
                high = maxIdx;
            } else {
                low = maxIdx + 1;
                high = len - 1;
            }

            while (low <= high) {
                int midIdx = (low + high) / 2;
                int midVal = nums[midIdx];
                if (midVal < target)
                    low = midIdx + 1;
                else if (midVal > target)
                    high = midIdx - 1;
                else
                    return midIdx;
            }
            return -1;
        }

        public int[] searchRange(int[] nums, int target) { // 34
            int[] ret = new int[]{-1, -1};
            int low = 0, high = nums.length - 1;
            while (low <= high) {
                int midIdx = (low + high) / 2;
                int midVal = nums[midIdx];
                if (midVal < target)
                    low = midIdx + 1;
                else if (midVal > target)
                    high = midIdx - 1;
                else {
                    int min = midIdx, max = midIdx;
                    while (--min >= 0) {
                        if (nums[min] != target)
                            break;
                    }
                    while (++max < nums.length) {
                        if (nums[max] != target)
                            break;
                    }
                    ret[0] = min + 1;
                    ret[1] = max - 1;
                    break;
                }
            }
            return ret;
        }

        public int searchInsert(int[] nums, int target) { // 35
            int low = 0, high = nums.length - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                int midVal = nums[mid];
                if (target > midVal) {
                    low = mid + 1;
                } else if (target < midVal) {
                    high = mid - 1;
                } else {
                    return mid;
                }
            }
            return low;
        }

        public boolean isValidSudoku(char[][] board) { // 36
            char[] lineDups = new char[9];
            char[] colDups = new char[9];
            char[] blockDups = new char[9];
            for (int i = 0; i < 9; ++i) {
                Arrays.fill(lineDups, '.');
                Arrays.fill(colDups, '.');
                Arrays.fill(blockDups, '.');
                for (int j = 0; j < 9; ++j) {
                    char lc = board[i][j];
                    if (lc != '.') {
                        if (lc == lineDups[lc - '1'])
                            return false;
                        lineDups[lc - '1'] = lc;
                    }
                    char cc = board[j][i];
                    if (cc != '.') {
                        if (cc == colDups[cc - '1'])
                            return false;
                        colDups[cc - '1'] = cc;
                    }
                    char bc = board[(i / 3) * 3 + j / 3][(i % 3) * 3 + j % 3];
                    if (bc != '.') {
                        if (bc == blockDups[bc - '1'])
                            return false;
                        blockDups[bc - '1'] = bc;
                    }
                }
            }
            return true;
        }

        public void solveSudoku(char[][] board) { // 37
            int[] bt = new int[9 * 9];

            for (int i = 0; i < 9; ++i) {
                int m = i * 9;
                for (int j = 0; j < 9; ++j) {
                    if (board[i][j] != '.') {
                        bt[m + j] = board[i][j] - '0';
                    }
                }
            }

            boolean back = false;
            for (int i = 0; i < 9 * 9; ) {
                if (board[i / 9][i % 9] != '.') {
                    if (back)
                        --i;
                    else
                        ++i;
                    continue;
                }

                int next = getNext(i, bt);
                bt[i] = next;
                if (next == 0) {
                    back = true;
                    --i;
                } else {
                    back = false;
                    ++i;
                }
            }

            for (int i = 0; i < 9 * 9; ++i) {
                board[i / 9][i % 9] = (char) (bt[i] + '0');
            }

//            StringBuilder sb = new StringBuilder(200);
//            for (int i = 0; i < 9; ++i) {
//                for (int j = 0; j < 9; ++j) {
//                    sb.append(board[i][j]).append(",");
//                }
//                sb.append("\n");
//            }
//            System.out.println(sb);
        }

        int getNext(int k, int[] bt) {
            int cur = bt[k];
            if (cur == 9) {
                return 0;
            }
            int[] tmp = new int[9];

            int row = k / 9;
            int col = k % 9;
            int b0 = (row / 3) * 3 * 9 + (col / 3) * 3;

            int rb = row * 9;
            for (int i = 0; i < 9; ++i) {
                int rv = bt[rb + i];
                if (rv != 0) {
                    tmp[rv - 1] = 1;
                }

                int cv = bt[col + i * 9];
                if (cv != 0) {
                    tmp[cv - 1] = 1;
                }

                int bk = b0 + (i / 3) * 9 + (i % 3);
                int bv = bt[bk];
                if (bv != 0) {
                    tmp[bv - 1] = 1;
                }
            }
            for (int i = cur; i < 9; ++i) {
                if (tmp[i] == 0) {
                    return i + 1;
                }
            }
            return 0;
        }

        public String countAndSay(int n) { // 38
            String num = "1";
            StringBuilder next = new StringBuilder();
            for (int i = 1; i < n; ++i) {
                char count = '0';
                char pre = num.charAt(0);
                for (int j = 0; j < num.length(); ++j) {
                    char c = num.charAt(j);
                    if (c != pre) {
                        next.append(count).append(pre);
                        pre = c;
                        count = '1';
                    } else {
                        ++count;
                    }
                }
                num = next.append(count).append(pre).toString();
                next.setLength(0);
            }
            return num;
        }

        public List<List<Integer>> combinationSum(int[] candidates, int target) { // 39
            final List<List<Integer>> ret = new ArrayList<>();
            Arrays.sort(candidates);
            final int len = candidates.length;

            for (int i = 0; i < len; ++i) {
                if (candidates[i] > target) {
                    break;
                }
                List<Integer> tmp = new ArrayList<>();
                List<Integer> idx = new ArrayList<>();
                int cv = target;
                for (int j = i; j < len; ) {
                    int v = candidates[j];
                    cv -= v;
                    if (cv == 0) {
                        tmp.add(v);
                        ret.add(new ArrayList<>(tmp));
                        cv += tmp.remove(tmp.size() - 1);
                        if (tmp.size() > 0) {
                            cv += tmp.remove(tmp.size() - 1);
                            j = idx.remove(idx.size() - 1) + 1;
                        }
                    } else if (cv < 0) {
                        cv += v;
                        cv += tmp.remove(tmp.size() - 1);
                        j = idx.remove(idx.size() - 1) + 1;
                    } else {
                        tmp.add(v);
                        idx.add(j);
                    }
                    if (tmp.isEmpty()) {
                        break;
                    }
                    while (j == len && tmp.size() > 1) {
                        cv += tmp.remove(tmp.size() - 1);
                        j = idx.remove(idx.size() - 1) + 1;
                    }
                }
            }
            return ret;
        }

        public List<List<Integer>> combinationSum39_2(int[] candidates, int target) { // 39-2
            final List<List<Integer>> ret = new ArrayList<>();
            Arrays.sort(candidates);
            sumDFS(target, 0, candidates, new ArrayList<>(), ret);
            return ret;
        }

        void sumDFS(int target, int from, int[] candidates, List<Integer> record, List<List<Integer>> ret) {
            if (target <= 0) {
                if (target == 0) {
                    ret.add(new ArrayList<>(record));
                }
                return;
            }

            for (int i = from, len = candidates.length; i < len; ++i) {
                int v = candidates[i];
                record.add(v);
                sumDFS(target - v, i, candidates, record, ret);
                record.remove(record.size() - 1);

                if (target <= v) {
                    return;
                }
            }
        }

        public List<List<Integer>> combinationSum40(int[] candidates, int target) { // 40
            final List<List<Integer>> ret = new ArrayList<>();
            Arrays.sort(candidates);
            sumDFS40(target, 0, candidates, new ArrayList<>(), ret);
            return ret;
        }

        void sumDFS40(int target, int from, int[] candidates, List<Integer> record, List<List<Integer>> ret) {
            if (target <= 0) {
                if (target == 0) {
                    ret.add(new ArrayList<>(record));
                }
                return;
            }

            for (int i = from, len = candidates.length; i < len; ++i) {
                int v = candidates[i];
                if (i > from && candidates[i - 1] == v) {
                    continue;
                }
                record.add(v);
                sumDFS40(target - v, i + 1, candidates, record, ret);
                record.remove(record.size() - 1);

                if (target <= v) {
                    return;
                }
            }
        }

        public int firstMissingPositive(int[] nums) { // 41
            final int len = nums.length;
            for (int i = 0; i < len; ++i) {
                int v = nums[i];
                if (v - 1 == i || v <= 0 || v > len) {
                    continue;
                }

                int ti = v - 1;
                int tv = nums[ti];
                if (tv != v) {
                    nums[ti] = v;
                    nums[i] = tv;
                    --i;
                }
            }
            for (int i = 0; i < len; ++i) {
                if (nums[i] != i + 1) {
                    return i + 1;
                }
            }
            return len + 1;
        }

        public int trap(int[] height) { // 42
            final int len = height.length;
            ArrayList<Integer> tmp = new ArrayList<>();
            int lv = 0, rv = -1, mv = -1;
            int lk = -1, rk = -1;
            for (int i = 0; i < len; ++i) {
                int cur = height[i];
                if (lv <= 0 || cur >= lv && mv < 0) {
                    lv = cur;
                    lk = i;
                    continue;
                }
                if (cur < rv) {
                    if (tmp.isEmpty()) {
                        tmp.add(lk);
                    }
                    tmp.add(rk);

                    lv = rv;
                    lk = rk;
                    mv = cur;
                    rv = rk = -1;

                    continue;
                }
                if (mv < 0 || cur < mv) {
                    mv = cur;
                    continue;
                }

                rv = cur;
                rk = i;
            }

            if (lv >= 0 && rv >= 0) {
                if (tmp.isEmpty()) {
                    tmp.add(lk);
                }
                tmp.add(rk);
            }

            for (int i = 1, size = tmp.size(); i < size - 1; ) {
                int tv = height[tmp.get(i)];
                if (tv <= height[tmp.get(i - 1)] && tv <= height[tmp.get(i + 1)]) {
                    tmp.remove(i);
                    --size;
                    --i;
                    if (i < 1) {
                        i = 1;
                    }
                } else {
                    ++i;
                }
            }

            int ret = 0;
            for (int i = 0, size = tmp.size(); i < size - 1; ++i) {
                int tk1 = tmp.get(i);
                int tk2 = tmp.get(i + 1);
                int tv1 = height[tk1];
                int tv2 = height[tk2];
                int v = Math.min(tv1, tv2);
                ret += v * (tk2 - tk1 - 1);
                for (int j = tk1 + 1; j < tk2; ++j) {
                    ret -= Math.min(v, height[j]);
                }
            }
            return ret;
        }

        public int trap2(int[] height) { // 42-2
            final int len;
            if (height == null || (len = height.length) == 0) {
                return 0;
            }
            int[] leftMax = new int[len];
            int[] rightMax = new int[len];
            for (int i = 1; i < len; ++i) {
                leftMax[i] = Math.max(height[i - 1], leftMax[i - 1]);
            }
            for (int i = len - 2; i >= 0; --i) {
                rightMax[i] = Math.max(height[i + 1], rightMax[i + 1]);
            }
            int ret = 0;
            for (int i = 1; i < len; ++i) {
                ret += Math.max(Math.min(leftMax[i], rightMax[i]) - height[i], 0);
            }
            return ret;
        }

        public String multiply(String num1, String num2) { // 43
            ArrayList<Character> result = new ArrayList<>();
            for (int len = num2.length(), i = len - 1; i >= 0; --i) {
                ArrayList<Character> arr = mulStr(num1, num2.charAt(i), len - 1 - i);
                System.out.println(arr);
                int arrLen = arr.size();
                result.ensureCapacity(arrLen + 1);
                int h = 0;
                int j = 0;
                for (; j < arrLen || h != 0; ++j) {
                    boolean nb = result.size() <= j;
                    int v = (j < arrLen ? (arr.get(j) - '0') : 0) + h;
                    if (!nb) {
                        v += result.get(j) - '0';
                    }
                    if (v > 9) {
                        h = 1;
                        v = v - 10;
                    } else {
                        h = 0;
                    }
                    if (!nb) {
                        result.set(j, (char) (v + '0'));
                    } else {
                        result.add((char) (v + '0'));
                    }
                }
            }

            char[] resArr = new char[result.size()];
            int j = 0;
            for (int i = result.size() - 1; i >= 0; --i) {
                resArr[j++] = result.get(i);
            }
            return String.valueOf(resArr);
        }

        ArrayList<Character> mulStr(String num1, char m, int z) {
            ArrayList<Character> result = new ArrayList<>();
            if (m == '0' || num1.equals("0")) {
                result.add('0');
                return result;
            }
            for (int i = 0; i < z; ++i) {
                result.add('0');
            }
            final int mv = m - '0';
            int h = 0;
            for (int len = num1.length(), i = len - 1; i >= 0; --i) {
                char c = num1.charAt(i);
                int r = (c - '0') * mv + h;
                result.add((char) (r % 10 + '0'));
                h = r / 10;
            }
            if (h != 0) {
                result.add((char) (h + '0'));
            }
            return result;
        }

        public boolean isMatch44(String s, String p) { // 44
            return subMatch44(s, p, 0, 0, s != null ? s.length() : 0, p != null ? p.length() : 0);
        }

        boolean subMatch44(String s, String p, int si, int pi, int sLen, int pLen) {
            if (si == sLen) {
                for (int i = pi; i < pLen; ++i) {
                    if (p.charAt(i) != '*') {
                        return false;
                    }
                }
                return true;
            }
            if (pi == pLen) {
                return false;
            }

            char pv = p.charAt(pi);
            while (pv == '*') {
                if (++pi == pLen) {
                    return true;
                }
                pv = p.charAt(pi);
            }
            int start = pi;
            while (pi < pLen) {
                pv = p.charAt(pi);
                if (pv == '*') {
                    break;
                }
                ++pi;
            }
            int subLen = pi - start;
            int m = (pi != pLen || start == 0) ? si : sLen - subLen;
            if (m < si) {
                m = si;
            }
            for (; m <= sLen - subLen; ++m) {
                boolean f = true;
                for (int n = 0; n < subLen; ++n) {
                    char tpv = p.charAt(start + n);
                    char tsv = s.charAt(m + n);
                    if (tpv != '?' && tpv != tsv) {
                        f = false;
                        break;
                    }
                }
                if (f) {
                    return subMatch44(s, p, m + subLen, pi, sLen, pLen);
                } else if (start == 0) {
                    break;
                }
            }
            return false;
        }

        public int jump(int[] nums) { // 45
            final int len = nums.length;
            if (len < 2) {
                return 0;
            }
            boolean[] record = new boolean[len];
            int level = 0;
            ArrayList<Integer> queue = new ArrayList<>();
            queue.add(0);
            ArrayList<Integer> subQueue = new ArrayList<>();

            int qIdx = 0;
            while (true) {
                int nIdx = queue.get(qIdx++);
                int v = nums[nIdx];
                int start = nIdx + 1;
                int end = nIdx + v;
                if (end + 1 >= len) {
                    break;
                }
                for (int i = start; i <= end; ++i) {
                    int nv = nums[i];
                    if (nv + i > end && !record[i]) {
                        subQueue.add(i);
                        record[i] = true;
                    }
                }

                if (qIdx >= queue.size()) {
                    ++level;
                    queue.clear();
                    ArrayList<Integer> tmp = queue;
                    queue = subQueue;
                    subQueue = tmp;
                    qIdx = 0;
                }

            }
            return level + 1;
        }

        public int jump45_2(int[] nums) { // 45-2
            final int len = nums.length;
            if (len < 2) {
                return 0;
            }

            int jump = 1;
            int startIdx = 1;
            int endIdx = nums[0];
            int maxIdx = endIdx;
            if (maxIdx < len - 1) {
                while (true) {
                    ++jump;
                    for (int i = startIdx; i <= endIdx; ++i) {
                        maxIdx = Math.max(i + nums[i], maxIdx);
                    }
                    if (maxIdx >= len - 1) {
                        break;
                    }
                    startIdx = endIdx + 1;
                    endIdx = maxIdx;
                }
            }
            return jump;
        }

        public List<List<Integer>> permute46(int[] nums) { // 46
            List<List<Integer>> ret = new ArrayList<>();
            final int len = nums.length;
            for (int i = 0; i < len; ++i) {
                permute46(nums, i + 1, ret);
            }
            return ret;
        }

        void permute46(int[] nums, int n, List<List<Integer>> ret) {
            if (n == 1) {
                List<Integer> rs = new ArrayList<>();
                rs.add(nums[0]);
                ret.add(rs);
                return;
            }
            List<List<Integer>> tmp = new ArrayList<>(ret);
            int size = tmp.size();
            ret.clear();
            int add = nums[n - 1];
            for (int i = 0; i < size; ++i) {
                List<Integer> rs = tmp.get(i);
                rs.add(add);
                ret.add(new ArrayList<>(rs));
                for (int j = rs.size() - 1; j > 0; --j) {
                    int v1 = rs.get(j);
                    int v0 = rs.get(j - 1);
                    rs.set(j, v0);
                    rs.set(j - 1, v1);
                    ret.add(new ArrayList<>(rs));
                }
            }
        }

        List<List<Integer>> permute46_dfs(int[] nums) {
            List<List<Integer>> ret = new ArrayList<>();
            final int len = nums.length;
            List<Integer> path = new ArrayList<>();
            boolean[] bPath = new boolean[len];
            permute46_dfs(nums, len, path, bPath, ret);
            return ret;
        }

        void permute46_dfs(int[] nums, int len, List<Integer> path, boolean[] bPath, List<List<Integer>> ret) {
            if (path.size() == len) {
                ret.add(new ArrayList<>(path));
                return;
            }
            for (int i = 0; i < len; ++i) {
                if (bPath[i]) {
                    continue;
                }
                path.add(nums[i]);
                bPath[i] = true;
                permute46_dfs(nums, len, path, bPath, ret);
                bPath[i] = false;
                path.remove(path.size() - 1);
            }
        }

        public List<List<Integer>> permuteUnique(int[] nums) { // 47
            Arrays.sort(nums);
            List<List<Integer>> ret = new ArrayList<>();
            final int len = nums.length;
            List<Integer> path = new ArrayList<>();
            boolean[] bPath = new boolean[len];
            permute47_dfs(nums, len, path, bPath, ret);
            return ret;
        }

        void permute47_dfs(int[] nums, int len, List<Integer> path, boolean[] bPath, List<List<Integer>> ret) {
            if (path.size() == len) {
                ret.add(new ArrayList<>(path));
                return;
            }
            int pre = Integer.MAX_VALUE;
            for (int i = 0; i < len; ++i) {
                if (bPath[i]) {
                    continue;
                }
                int v = nums[i];
                if (v == pre) {
                    continue;
                }
                pre = v;
                path.add(v);
                bPath[i] = true;
                permute47_dfs(nums, len, path, bPath, ret);
                bPath[i] = false;
                path.remove(path.size() - 1);
            }
        }

        public void rotate(int[][] matrix) { // 48
            final int n = matrix.length;
            if (n < 2) {
                return;
            }
            int e = n / 2;
            for (int m = 0; m < e; ++m) {
                int min = m;
                int max = n - 2 * m + min - 1;

                int v0 = matrix[min][min];
                matrix[min][min] = matrix[max][min];
                matrix[max][min] = matrix[max][max];
                matrix[max][max] = matrix[min][max];
                matrix[min][max] = v0;
                for (int i = min + 1; i < max; ++i) {
                    int vi = matrix[min][i];
                    int offset = max - i + m;
                    matrix[min][i] = matrix[offset][min];
                    matrix[offset][min] = matrix[max][offset];
                    matrix[max][offset] = matrix[i][max];
                    matrix[i][max] = vi;
                }
            }
        }

        public List<List<String>> groupAnagrams(String[] strs) { // 49
            HashMap<String, List<String>> tmp = new HashMap<>();
            final int len = strs.length;
            for (int i = 0; i < len; i++) {
                String s = strs[i];
                byte[] sa = s.getBytes();
                Arrays.sort(sa);
                String ts = new String(sa);
                List<String> r = tmp.computeIfAbsent(ts, k -> new ArrayList<>());
                r.add(s);
            }
            return new ArrayList<>(tmp.values());
        }

        public double myPow(double x, int n) { // 50
            if (x == 0) {
                return 0;
            }
            if (x == 1) {
                return x;
            }
            if (n == 0) {
                return 1;
            }
            long pn = n < 0 ? -(long) n : n;
            double r = 1;
            double pre = x;
            while (pn != 0) {
                if ((pn & 1) != 0) {
                    r *= pre;
                }
                pre *= pre;
                pn >>= 1;
            }
            if (n < 0) {
                r = 1 / r;
            }
            return r;
        }

        public List<List<String>> solveNQueens(int n) { // 51
            List<List<String>> ret = new ArrayList<>();
            char[][] arr = new char[n][n];
            for (int i = 0; i < n; ++i) {
                Arrays.fill(arr[i], '.');
            }
            queen(n, 0, arr, ret);
            return ret;
        }

        void queen(int n, int row, char[][] arr, List<List<String>> ret) {
            if (row == n) {
                List<String> s = new ArrayList<>(n);
                ret.add(s);
                for (int i = 0; i < n; ++i) {
                    s.add(new String(arr[i]));
                }
                return;
            }

            outer:
            for (int col = 0; col < n; ++col) {
                for (int i = 0; i < n; ++i) { // column check
                    if (arr[i][col] == 'Q') {
                        continue outer;
                    }
                }
                for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; --i, --j) { // top-left check
                    if (arr[i][j] == 'Q') {
                        continue outer;
                    }
                }
                for (int i = row - 1, j = col + 1; i >= 0 && j < n; --i, ++j) { // top-right check
                    if (arr[i][j] == 'Q') {
                        continue outer;
                    }
                }

                arr[row][col] = 'Q';
                queen(n, row + 1, arr, ret);
                arr[row][col] = '.';
            }
        }

        public int totalNQueens(int n) { // 52
            if (n < 2) {
                return n;
            }
            boolean[][] arr = new boolean[n][n];
            int[] ret = new int[1];
            queen(n, 0, arr, ret);
            return ret[0];
        }

        void queen(int n, int row, boolean[][] arr, int[] ret) {
            if (row == n) {
                ret[0] += 1;
                return;
            }

            outer:
            for (int col = 0; col < n; ++col) {
                for (int i = 0; i < n; ++i) { // column check
                    if (arr[i][col]) {
                        continue outer;
                    }
                }
                for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; --i, --j) { // top-left check
                    if (arr[i][j]) {
                        continue outer;
                    }
                }
                for (int i = row - 1, j = col + 1; i >= 0 && j < n; --i, ++j) { // top-right check
                    if (arr[i][j]) {
                        continue outer;
                    }
                }

                arr[row][col] = true;
                queen(n, row + 1, arr, ret);
                arr[row][col] = false;
            }
        }

        public int maxSubArray(int[] nums) { // 53
            int maxSum = Integer.MIN_VALUE;
            int sum = 0;
            for (int i = 0, len = nums.length; i < len; ++i) {
                sum += nums[i];
                if (sum > maxSum) {
                    maxSum = sum;
                }
                if (sum <= 0) {
                    sum = 0;
                }
            }
            return maxSum;
        }

        public List<Integer> spiralOrder(int[][] matrix) { // 54
            int m = matrix.length;
            int n = matrix[0].length;
            List<Integer> ret = new ArrayList<>(m * n);
            int hr = Math.min(m / 2 + m % 2, n / 2 + n % 2);
            for (int i = 0; i < hr; i++) {
                int c1 = n - i - 1;
                int r1 = m - i - 1;
                for (int ci = i; ci <= c1; ++ci) {
                    ret.add(matrix[i][ci]);
                }
                for (int ri = i + 1; ri <= r1 - 1; ++ri) {
                    ret.add(matrix[ri][c1]);
                }
                if (r1 != i) {
                    for (int ci = c1; ci >= i; --ci) {
                        ret.add(matrix[r1][ci]);
                    }
                }
                if (c1 != i) {
                    for (int ri = r1 - 1; ri >= i + 1; --ri) {
                        ret.add(matrix[ri][i]);
                    }
                }
            }
            return ret;
        }

        public boolean canJump(int[] nums) { // 55
            final int len = nums.length;
            int start = 0;
            int end = nums[0];
            int max = Math.max(start, end);
            while (true) {
                if (max + 1 >= len) {
                    return true;
                }
                for (int i = start + 1; i <= end; i++) {
                    max = Math.max(max, i + nums[i]);
                }
                if (max <= end) {
                    return false;
                }
                start = end;
                end = max;
            }
        }

        public int[][] merge(int[][] intervals) { // 56
            int len = intervals.length;
            Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

            int count = len;
            int preIdx = -1;
            int pre1 = 0;
            for (int i = 0; i < len; ++i) {
                int v0 = intervals[i][0];
                int v1 = intervals[i][1];
                if (preIdx < 0 || v0 > pre1) {
                    preIdx = i;
                    pre1 = v1;
                    continue;
                }

                pre1 = Math.max(v1, pre1);
                intervals[preIdx][1] = pre1;
                intervals[i] = null;
                --count;
            }

            int j = 0;
            int[][] ret = new int[count][2];
            for (int i = 0; i < len; i++) {
                if (intervals[i] != null) {
                    ret[j++] = intervals[i];
                }
            }
            return ret;
        }

        public int[][] insert(int[][] intervals, int[] newInterval) { // 57
            int len = intervals.length;
            int n0 = newInterval[0], n1 = newInterval[1];
            int li = -1, ri = len - 1;
            for (int i = 0; i < len; i++) {
                if (n0 > intervals[i][1]) {
                    continue;
                }
                if (li < 0) {
                    li = i;
                }
                if (n1 <= intervals[i][1]) {
                    if (n1 >= intervals[i][0]) {
                        ri = i;
                    } else {
                        ri = i - 1;
                    }
                    break;
                }
            }

            if (li < 0) {
                int[][] ret = Arrays.copyOf(intervals, len + 1);
                ret[len] = newInterval;
                return ret;
            }

            int[][] ret = new int[len - (ri - li)][2];
            newInterval[0] = Math.min(intervals[li][0], n0);
            newInterval[1] = ri < 0 ? n1 : Math.max(intervals[ri][1], n1);
            int j = 0;
            for (int i = 0; i < li; ++i) {
                ret[j++] = intervals[i];
            }
            ret[j++] = newInterval;
            for (int i = ri + 1; i < len; ++i) {
                ret[j++] = intervals[i];
            }
            return ret;
        }

        public int lengthOfLastWord(String s) { // 58
            int lastIdx = -1;
            for (int i = s.length() - 1; i >= 0; --i) {
                char c = s.charAt(i);
                if (c == ' ' && lastIdx >= 0) {
                    return lastIdx - i;
                } else if (c != ' ' && lastIdx < 0) {
                    lastIdx = i;
                }
            }
            return lastIdx < 0 ? 0 : lastIdx + 1;
        }

        public int[][] generateMatrix(int n) { // 59
            int[][] ret = new int[n][n];
            int count = n / 2 + n % 2;
            int v = 0;
            for (int i = 0; i < count; ++i) {
                int mc = n - i - 1;
                for (int c = i; c <= mc; ++c) {
                    ret[i][c] = ++v;
                }
                for (int r = i + 1; r <= mc - 1; ++r) {
                    ret[r][mc] = ++v;
                }
                if (mc != i) {
                    for (int c = mc; c >= i; --c) {
                        ret[mc][c] = ++v;
                    }
                }
                for (int r = mc - 1; r >= i + 1; --r) {
                    ret[r][i] = ++v;
                }
            }
            return ret;
        }

        public String getPermutation(int n, int k) { // 60
            int[] ck = new int[1];
            char[] ret = new char[n];
            boolean[] used = new boolean[n];
            permutation_60(n, k, 0, ck, ret, used);
            return String.valueOf(ret);
        }

        boolean permutation_60(int n, int k, int cn, int[] ck, char[] ret, boolean[] used) {
            if (cn == n) {
                ck[0] = ck[0] + 1;
                if (ck[0] == k) {
                    return true;
                }
                return false;
            }

            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    used[i] = true;
                    ret[cn++] = (char) (i + '1');
                    if (permutation_60(n, k, cn, ck, ret, used)) {
                        return true;
                    }
                    ret[--cn] = 0;
                    used[i] = false;
                }
            }
            return false;
        }

        public String getPermutation_60_2(int n, int k) { // 60
            char[] ret = new char[n];
            boolean[] used = new boolean[n];
            int[] totals = new int[n];
            int total = 1;
            for (int i = 0; i < n; ++i) {
                total *= i + 1;
                totals[i] = total;
            }
            getPermutation_60_2(n, k, totals, ret, used);
            return String.valueOf(ret);
        }

        void getPermutation_60_2(int n, int k, int[] totals, char[] ret, boolean[] used) {
            int total = n < 2 ? 1 : totals[n - 2];
            int m = k % total;
            int c = k / total + (m == 0 ? 0 : 1);
            k = m == 0 ? total : m;
            int len = ret.length;
            for (int i = 0; i < len; ++i) {
                if (!used[i] && --c == 0) {
                    ret[len - n] = (char) (i + '1');
                    used[i] = true;
                    if (n > 1) {
                        getPermutation_60_2(n - 1, k, totals, ret, used);
                    }
                    return;
                }
            }
        }

        public ListNode rotateRight(ListNode head, int k) { // 61
            if (head == null || head.next == null) {
                return head;
            }
            int count = 0;
            ListNode node = head;
            while (true) {
                ++count;
                ListNode n = node.next;
                if (n == null) {
                    node.next = head;
                    break;
                }
                node = n;
            }
            k = count - k % count;
            while (--k >= 0) {
                node = node.next;
            }
            head = node.next;
            node.next = null;
            return head;
        }

        public int uniquePaths(int m, int n) { // 62 C(m-1+n-1, n-1)
            long ret = 1;
            int m0 = m - 1;
            int n0 = n - 1;
            int total = m0 + n0;
            int min = Math.min(m0, n0);
            long tmp = 1;
            for (int i = 0; i < min; i++) {
                ret *= total - i;
                tmp *= min - i;
            }
            return (int) (ret / tmp);
        }

        public int uniquePaths_62_2(int m, int n) { // 62-2
            int[] count = new int[1];
            paths_62_2(m - 1, n - 1, count);
            return count[0];
        }

        void paths_62_2(int right, int down, int[] count) {
            if (right == 0 || down == 0) {
                count[0] += 1;
                return;
            }
            paths_62_2(right - 1, down, count);
            paths_62_2(right, down - 1, count);
        }

        public int paths_62_3(int m, int n) {
            int[][] dp = new int[m][n];
            dp[0][0] = 0;
            Arrays.fill(dp[0], 1);
            for (int i = 1; i < m; ++i) {
                dp[i][0] = 1;
            }
            for (int j = 1; j < n; ++j) {
                for (int i = 1; i < m; ++i) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
            return dp[m - 1][n - 1];
        }

        public int uniquePathsWithObstacles(int[][] obstacleGrid) { // 63
            int m = obstacleGrid.length;
            int n = obstacleGrid[0].length;
            int[][] dp = new int[m][n];
            dp[0][0] = obstacleGrid[0][0] == 0 ? 1 : 0;
            for (int i = 1; i < n; ++i) {
                if (obstacleGrid[0][i] == 0)
                    dp[0][i] = dp[0][i - 1];
            }
            for (int i = 1; i < m; ++i) {
                if (obstacleGrid[i][0] == 0)
                    dp[i][0] = dp[i - 1][0];
            }
            for (int j = 1; j < n; ++j) {
                for (int i = 1; i < m; ++i) {
                    if (obstacleGrid[i][j] == 0) {
                        dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                    }
                }
            }
            return dp[m - 1][n - 1];
        }

        public int minPathSum(int[][] grid) { // 64
            int m = grid.length;
            int n = grid[0].length;
            int[][] dp = new int[m][n];
            for (int i = 0; i < m; i++) {
                dp[i][0] = grid[i][0] + (i != 0 ? dp[i - 1][0] : 0);
            }
            for (int i = 0; i < n; i++) {
                dp[0][i] = grid[0][i] + (i != 0 ? dp[0][i - 1] : 0);
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
                }
            }
            return dp[m - 1][n - 1];
        }

        public boolean isNumber(String s) { // 65
            // -2.5e-4
            return validNumber(s.trim(), 0, -2, 0);
        }

        boolean validNumber(String s, int pos, int dot, int e) {
            final int len = s.length();
            boolean num = false;
            int sign = -2;
            for (int i = pos; i < len; ++i) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9') {
                    if (sign < -1) {
                        sign = i - 1;
                    }
                    num = true;
                } else if (c == '+' || c == '-') {
                    if (sign >= -1) {
                        return false;
                    }
                    sign = i;
                    char afterSign;
                    if (sign == len - 1 || ((afterSign = s.charAt(sign + 1)) != '.' && (afterSign > '9' || afterSign < '0'))) {
                        return false;
                    }
                } else if (c == '.') {
//                    char afterDot;
                    if (dot >= -1) { //|| i == len - 1 || (afterDot = s.charAt(i + 1)) > '9' || afterDot < '0') {
                        return false;
                    }
                    if (sign < -1) {
                        sign = -1;
                    }
                    dot = i;
                } else if (c == 'e') {
                    char preE;
                    if (!num || e > 0 || ((preE = s.charAt(i - 1)) != '.' && (preE > '9' || preE < '0'))) {
                        return false;
                    }
                    e = i;
                    return validNumber(s, i + 1, -1, e);
                } else {
                    return false;
                }
            }
            return num;
        }

        public int[] plusOne(int[] digits) { // 66
            final int len = digits.length;
            int h = 0;
            for (int i = len - 1; i >= 0; --i) {
                int v = digits[i] + h;
                if (i == len - 1) {
                    ++v;
                }
                if (v > 9) {
                    h = 1;
                    v -= 10;
                } else {
                    h = 0;
                }
                digits[i] = v;
            }
            if (h != 0) {
                int[] ret = new int[len + 1];
                ret[0] = h;
                for (int i = 0; i < len; ++i) {
                    ret[i + 1] = digits[i];
                }
                return ret;
            }
            return digits;
        }

        public String addBinary(String a, String b) { // 67
            String ret = "";
            int h = 0;
            for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; --i, --j) {
                int v = h;
                if (i >= 0) {
                    v += a.charAt(i) - '0';
                }
                if (j >= 0) {
                    v += b.charAt(j) - '0';
                }
                if (v == 3) {
                    h = 1;
                    v = 1;
                } else if (v == 2) {
                    h = 1;
                    v = 0;
                } else {
                    h = 0;
                }
                ret = v + ret;
            }
            if (h != 0) {
                ret = h + ret;
            }
            return ret;
        }

        public List<String> fullJustify(String[] words, int maxWidth) { // 68
            ArrayList<String> ret = new ArrayList<>();
            final int len = words.length;
            ArrayList<Integer> wCounts = new ArrayList<>();
            int curLen = 0;
            for (int i = 0; i < len; ++i) {
                int wordLen = words[i].length();
                if (curLen != 0) {
                    curLen += 1;
                }
                curLen += wordLen;
                if (curLen > maxWidth) {
                    wCounts.add(i - 1);
                    curLen = wordLen;
                }
            }

            int last = -1;
            for (int i = 0, size = wCounts.size(); i < size; ++i) {
                int start = i == 0 ? 0 : wCounts.get(i - 1) + 1;
                int end = last = wCounts.get(i);
                int sp = end - start;
                int minLen = 0;
                for (int j = start; j <= end; ++j) {
                    minLen += words[j].length();
                }
                int es = maxWidth - minLen;
                StringBuilder sb = new StringBuilder();
                if (sp == 0) {
                    sb.append(words[start]).append(" ".repeat(es));
                } else {
                    int avg = es / sp;
                    int left = es % sp;
                    String ts = " ".repeat(avg);
                    for (int j = start; j <= end; ++j) {
                        sb.append(words[j]);
                        if (j != end) {
                            sb.append(ts);
                        }
                        if (--left >= 0) {
                            sb.append(" ");
                        }
                    }
                }
                ret.add(sb.toString());
            }
            StringBuilder sb = new StringBuilder();
            for (int i = last + 1; i < len; ++i) {
                sb.append(words[i]);
                if (i != len - 1) {
                    sb.append(" ");
                }
            }
            sb.append(" ".repeat(maxWidth - sb.length()));
            ret.add(sb.toString());
            return ret;
        }

        public int mySqrt(int x) { // 69
            int low = 1, high = x;
            int mid = (int) (((long) low + high) / 2);
            int pre = mid;
            while (low < high) {
                long v = (long) mid * mid;
                if (v > x) {
                    high = mid;
                } else if (v < x) {
                    low = mid;
                } else {
                    return mid;
                }
                pre = mid;
                mid = (low + high) / 2;
                if (pre == mid) {
                    pre = mid;
                    break;
                }
            }
            return pre;
        }

        public int climbStairs(int n) { // 70
            long ret = 0;
            int c = n / 2 + n % 2;
            for (int i = n; i >= c; --i) {
                ret += c(i, n - i);
            }
            return (int) ret;
        }

        long c(int n, int m) { // c(n,m) = n!/(n-m)!m!
            long ret = 1;
            int min = Math.min(m, n - m);
            long tmp = 1;
            for (int i = 0; i < min; i++) {
                ret *= n - i;
                tmp *= min - i;
                if (ret % tmp == 0) {
                    ret = ret / tmp;
                    tmp = 1;
                }
            }
            return ret / tmp;
        }

        public int climbStairs2(int n) { // 70-2
            if (n == 1) {
                return 1;
            } else if (n == 2) {
                return 2;
            }
            return climbStairs2(n - 1) + climbStairs2(n - 2);
        }

        public int climbStairs3(int n) { // 70-3
            int[] dp = new int[n + 1];
            dp[0] = 1;
            dp[1] = 1;
            for (int i = 2; i <= n; ++i) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            return dp[n];
        }

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        ListNode l1 = new ListNode(2, new ListNode(4));
//        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
//        ListNode ret = solution.addTwoNumbers(l1, l2);

//        System.out.println(solution.clen("abcabcbb"));
//        System.out.println(solution.clen("bbbbb"));
//        System.out.println(solution.clen("pwwkew"));
//        System.out.println(solution.clen(" "));

//        int[] nums1 = {1, 3}, nums2 = {2};
//        System.out.println(solution.findMedianSortedArrays(nums1, nums2));

//        System.out.println(solution.isHuiwen("cbbd", 0, 1));
//        System.out.println(solution.convert("LEETCODEISHIRING", 2));
//        int a = 1534236469;
//        System.out.println(a);
//        System.out.println(solution.reverse(a));

//        String s = " 21474836469 ";
//        System.out.println(s);
//        System.out.println(solution.myAtoi(s));

//        String s = "-1";
//        System.out.println(solution.isHuiwen(s, 0, s.length() - 1));

//        System.out.println(solution.isMatch("aaaa", "aaa*"));
//        System.out.println(solution.isMatch("aaa", "ab*a*c*a"));

//        int[] h = {1, 8, 6, 2, 5, 4, 8, 3, 7};
//        System.out.println(solution.maxArea(h));

//        System.out.println(solution.intToRoman(58));

//        System.out.println(solution.romanToInt("MCMXCIV"));

//        String[] strs = {"dog", "racecar", "car"};
//        System.out.println(solution.longestCommonPrefix(strs));

//        int[] nums = {-1, 2, 1, -4};
//        System.out.println(solution.threeSum(nums));

//        System.out.println(solution.threeSumClosest(nums, 1));

//        System.out.println(solution.letterCombinations("234"));
//        int[] nums = {-5, -4, -2, -2, -2, -1, 0, 0, 1};
//        System.out.println(solution.fourSum(nums, -9));

//        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
//        System.out.println(solution.removeNthFromEnd(head, 5));

//        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
//        ListNode l2 = new ListNode(1, new ListNode(2, new ListNode(5)));
//        System.out.println(solution.mergeTwoLists(l1, l2));

//        System.out.println(solution.generateParenthesis(8));
//        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
//        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
//        ListNode l3 = new ListNode(2, new ListNode(6, new ListNode(7)));
//        ListNode[] lists = {l1, l2, l3};
//        System.out.println(solution.mergeKLists(lists));

//        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
//        System.out.println(solution.swapPairs(head));

//        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
//        System.out.println(solution.reverseKGroup(head, 3));

//        int[] nums = new int[]{1, 2, 3};
//        System.out.println(solution.removeDuplicates(nums));

//        int[] nums = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
//        System.out.println(solution.removeElement(nums, 2));

//        String haystack = "aaa", needle = "aaaa";
//        System.out.println(solution.strStr(haystack, needle));

//        System.out.println(solution.divide(5, 2));

//        System.out.println(solution.findSubstring("a", new String[]{"a"}));
//        System.out.println(solution.findSubstring("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "good"}));
//        System.out.println(solution.findSubstring("dddddddddddd", new String[]{"dddd", "dddd"}));
//        System.out.println(solution.findSubstring("bcabbcaabbccacacbabccacaababcbb", new String[]{"c", "b", "a", "c", "a", "a", "a", "b", "c"}));
//        System.out.println(solution.findSubstring("ababababab", new String[]{"ababa", "babab"}));

//        int[] nums = new int[]{2, 4, 3, 2, 1};
//        solution.nextPermutation(nums);
//        System.out.println(nums);

//        System.out.println(solution.longestValidParentheses("(())((()))(()(())(()(())"));
//        System.out.println(solution.longestValidParentheses2("(())"));

//        int[] nums = new int[]{6, 5};
//        System.out.println(solution.search(nums, 6));

//        int[] nums = new int[]{1};
//        int[] ret = solution.searchRange(nums, 0);
//        System.out.println(ret[0] + "," + ret[1]);

//        int[] nums = new int[]{};
//        System.out.println(solution.searchInsert(nums, 0));

//        char[][] board = new char[][]{
//                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
//                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
//                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
//                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
//                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
//                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
//                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
//                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
//                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
//        };
//        System.out.println(solution.isValidSudoku(board));
//        solution.solveSudoku(board);

//        System.out.println(solution.countAndSay(4));

//        int[] nums = new int[]{2, 3, 5};
//        System.out.println(solution.combinationSum(nums, 8));
//        System.out.println(solution.combinationSum39_2(nums, 8));

//        int[] nums = new int[]{10, 1, 2, 7, 6, 1, 5};
//        System.out.println(solution.combinationSum40(nums, 8));

//        int[] nums = new int[]{1, 2, 2, 2, 5};
//        System.out.println(solution.combinationSum40(nums, 5));


//        int[] nums = new int[]{13, 12, -1};
//        System.out.println(solution.firstMissingPositive(nums));

//        int[] nums = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
//        nums = new int[]{4, 2, 0, 3, 2, 5};
//        nums = new int[]{5, 4, 1, 2};
//        nums = new int[]{8, 8, 1, 5, 6, 2, 5, 3, 3, 9};
//        System.out.println(solution.trap(nums));
//        System.out.println(solution.trap2(nums));

//        System.out.println(solution.multiply("0", "999"));

//        System.out.println(solution.isMatch44("aa", "a"));
//        System.out.println(solution.isMatch44("aa", "*"));
//        System.out.println(solution.isMatch44("cb", "?a"));
//        System.out.println(solution.isMatch44("adceb", "*a*b"));
//        System.out.println(solution.isMatch44("aaaa", "**a"));
//        System.out.println(solution.isMatch44("abaaabaabaaccabc", "*a***b***a***cc**b*"));
//        System.out.println(solution.isMatch44("abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb",
//                "**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb"));

//        System.out.println(solution.isMatch44("abce", "abc*??"));
//        System.out.println(solution.isMatch44("aaab", "b**"));

//        System.out.println(solution.jump(new int[]{2, 3, 1, 1, 4}));
//        System.out.println(solution.jump(new int[]{8, 2, 4, 4, 4, 9, 5, 2, 5, 8, 8, 0, 8, 6, 9, 1, 1, 6, 3, 5, 1, 2, 6, 6, 0, 4, 8, 6, 0, 3, 2, 8, 7, 6, 5, 1, 7, 0, 3, 4, 8, 3, 5, 9, 0, 4, 0, 1, 0, 5, 9, 2, 0, 7, 0, 2, 1, 0, 8, 2, 5, 1, 2, 3, 9, 7, 4, 7, 0, 0, 1, 8, 5, 6, 7, 5, 1, 9, 9, 3, 5, 0, 7, 5}));

//        System.out.println(solution.jump45_2(new int[]{2, 3, 1, 1, 4}));
//        System.out.println(solution.jump45_2(new int[]{8, 2, 4, 4, 4, 9, 5, 2, 5, 8, 8, 0, 8, 6, 9, 1, 1, 6, 3, 5, 1, 2, 6, 6, 0, 4, 8, 6, 0, 3, 2, 8, 7, 6, 5, 1, 7, 0, 3, 4, 8, 3, 5, 9, 0, 4, 0, 1, 0, 5, 9, 2, 0, 7, 0, 2, 1, 0, 8, 2, 5, 1, 2, 3, 9, 7, 4, 7, 0, 0, 1, 8, 5, 6, 7, 5, 1, 9, 9, 3, 5, 0, 7, 5}));

//        System.out.println(solution.permute46(new int[]{1, 2, 3}));
//        System.out.println(solution.permute46_dfs(new int[]{1, 2, 3}));

//        System.out.println(solution.permuteUnique(new int[]{1, 2, 1, 1}));

//        int[][] matrix = new int[][]{
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9},
//        };
//        int[][] matrix = new int[][]{
//                {5, 1, 9, 11},
//                {2, 4, 8, 10},
//                {13, 3, 6, 7},
//                {15, 14, 12, 16}
//        };
//        int[][] matrix = new int[][]{
//                {1, 2},
//                {3, 4},
//        };
//        int[][] matrix = new int[][]{
//                {1, 2, 3, 4, 5},
//                {6, 7, 8, 9, 10},
//                {11, 12, 13, 14, 15},
//                {16, 17, 18, 19, 20},
//                {21, 22, 23, 24, 25}};
//        int len = matrix.length;
//        solution.rotate(matrix);
//        for (int i = 0; i < len; i++) {
//            for (int j = 0; j < len; j++) {
//                System.out.print(matrix[i][j] + ", ");
//            }
//            System.out.println();
//        }

//        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
//        System.out.println(solution.groupAnagrams(strs));

//        System.out.println(solution.myPow(2, 11));
//        System.out.println(Math.pow(2, 11));

//        System.out.println(solution.solveNQueens(12));

//        System.out.println(solution.totalNQueens(4));

//        System.out.println(solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));

//        int[][] matrix = new int[][]{
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9},
//        };
//        int[][] matrix = new int[][]{
//                {1, 4},
//                {5, 8},
//                {9, 12},
//                {10, 11},
//                {13, 14}
//        };
//        int[][] matrix = new int[][]{{1},{2},{3},{4},{5},{6},{7},{8},{9},{10}};
//        int[][] matrix = new int[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}};
//        System.out.println(solution.spiralOrder(matrix));

//        System.out.println(solution.canJump(new int[]{3, 2, 1, 0, 4}));

//        int[][] matrix = new int[][]{{3, 6}};
//        System.out.println(solution.merge(matrix));

//        int[][] matrix0 = new int[][]{{2, 4}, {7, 9}, {12, 18}};
//        int[] matrix1 = new int[]{125, 130};
//        int[][] ret = solution.insert(matrix0, matrix1);
//        for (int i = 0, len = ret.length; i < len; i++) {
//            System.out.print(ret[i][0] + "," + ret[i][1] + "; ");
//        }

//        System.out.println(solution.lengthOfLastWord(" a  "));

//        int[][] ret = solution.generateMatrix(6);
//        for (int i = 0, len = ret.length; i < len; i++) {
//            for (int j = 0; j < len; j++) {
//                System.out.print(ret[i][j] + ",");
//            }
//            System.out.println();
//        }

//        System.out.println(solution.getPermutation(8, 20));
//        System.out.println(solution.getPermutation_60_2(8, 20));

//        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
//        ListNode head = new ListNode(1);
//        System.out.println(solution.rotateRight(head, 6));

//        System.out.println(solution.uniquePaths(51, 9));
//        System.out.println(solution.uniquePaths_62_2(51, 9));
//        System.out.println(solution.paths_62_3(51, 9));

//        int[][] obstacleGrid = {{0, 1, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
//        int[][] obstacleGrid = {{1,0}};
//        int[][] obstacleGrid = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
//        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid));

//        int[][] grid = {{1, 1}};
//        System.out.println(solution.minPathSum(grid));

//        System.out.println(solution.isNumber("0"));
//        System.out.println(solution.isNumber(" 0.1 "));
//        System.out.println(solution.isNumber("abc"));
//        System.out.println(solution.isNumber("1 a"));
//        System.out.println(solution.isNumber("2e10"));
//        System.out.println(solution.isNumber(" -90e3   "));
//        System.out.println(solution.isNumber(" 1e"));
//        System.out.println(solution.isNumber("e3"));
//        System.out.println(solution.isNumber(" 6e-1"));
//        System.out.println(solution.isNumber(" 99e2.5 "));
//        System.out.println(solution.isNumber("53.5e93"));
//        System.out.println(solution.isNumber(" --6 "));
//        System.out.println(solution.isNumber("-+3"));
//        System.out.println(solution.isNumber("95a54e53"));
//        System.out.println(solution.isNumber("+.8"));
//        System.out.println(solution.isNumber("46.e3"));

//        int[] digits = {9, 9};
//        System.out.println(solution.plusOne(digits));

//        System.out.println(solution.addBinary("1111", "1111"));

//        System.out.println(solution.fullJustify(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16));
//        System.out.println(solution.fullJustify(new String[]{"What", "must", "be", "acknowledgment", "shall", "be"}, 16));
//        System.out.println(solution.fullJustify(new String[]{"What", "shall", "be"}, 26));

//        System.out.println(solution.mySqrt(2147483647));
//        System.out.println(Math.sqrt(2147483647));

        System.out.println(solution.climbStairs(45));
        System.out.println(solution.climbStairs2(45));
        System.out.println(solution.climbStairs3(45));

    }


}

