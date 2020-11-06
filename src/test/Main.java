package test;

import javax.swing.*;
import java.lang.reflect.Array;
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

        System.out.println(solution.countAndSay(4));

    }


}
