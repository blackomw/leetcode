package test;

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
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6, new ListNode(7)));
        ListNode[] lists = {l1, l2, l3};
        System.out.println(solution.mergeKLists(lists));
    }


}