import java.util.*;
import java.util.List;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static List<Integer> V = new ArrayList<>();
    public static List<Map<Integer, Integer>> E = new ArrayList<>();

    public static void main(String[] args) {
        boolean isRun = true;
        System.out.print("մուտքագրեք գրաֆի գագաթների քանակը - ");
        int g = sc.nextInt();
        int v1;
        int v2;
        int n;
        for (int i = 1; i <= g; i++) {
            V.add(i);
        }
        System.out.println("մուտքագրեք գրաֆի կողեր կազմող զույգերը օգտագործելով 1-" + g);
        while (isRun) {
            Map<Integer, Integer> e = new HashMap<>();

            System.out.print("առաջին գագաթ - ");
            v1 = sc.nextInt();
            System.out.print("երկրորդ գագաթ - ");
            v2 = sc.nextInt();
            if (v1 != v2) {
                e.put(v1, v2);
                E.add(e);
            }
            System.out.print("0 ավարտելու համար - ");
            n = sc.nextInt();
            if (v1 > g || v2 > g || n == 0) {
                isRun = false;
            }
        }
        System.out.println(E);
        searchIndependentTrangles();



    }



    private static void searchIndependentTrangles() {
        int[][] A=graphToMatrix();
        List<List<Integer>> tr = trangles(A);
        if (tr!= null) {
            int [][] trA= trangleToMatrix(tr);
            List<List<Integer>> maxIndTr=new ArrayList<>();
            List<Integer> S= maxIndependentPeak(trA);
            for (int i = 0; i < S.size(); i++) {
                maxIndTr.add(tr.get(S.get(i)-1));
            }
            System.out.print("մաքսիմալ անկախ եռանկյունիների բազմություն - ");
            for (int i = 0; i < S.size(); i++) {
                System.out.print(maxIndTr.get(i));
            }

        } else {
            System.out.println("erankyuniner chkan");
        }

    }

    private static int [][] graphToMatrix() {
        int g = V.size();
        int[][] A = new int[g][g];

        for (int i = 1; i <= g; i++) {
            for (int j = 0; j < E.size(); j++) {
                if (E.get(j).get(i) != null) {
                    A[i - 1][E.get(j).get(i) - 1] = 1;
                    A[E.get(j).get(i) - 1][i - 1] = 1;
                }
            }
        }
        for (int i = 0; i < g; i++) {
            A[i][i] = 0;
        }

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                System.out.print(A[i][j] + ", ");
            }
            System.out.println();
        }
        return A;
    }

    private static List<List<Integer>> trangles(int[][] A) {
        int n = A.length;
        List<List<Integer>> tr = new ArrayList<>();
        for (int a = 0; a < n; a++) {
            for (int b = a + 1; b < n; b++) {
                if ((A[a][b]) == 0) {
                    continue;
                }
                for (int c = b + 1; c < n; c++) {
                    if (A[b][c] != 0 && A[c][a] != 0) {
                        List<Integer> t = new ArrayList<>();
                        System.out.print("եռանկյունիներ - ");
                        System.out.println((a + 1) + "," + (b + 1) + "," + (c + 1));
                        t.add(a + 1);
                        t.add(b + 1);
                        t.add(c + 1);
                        tr.add(t);

                    }

                }

            }
        }
        return tr;
    }


    private static int [][] trangleToMatrix(List<List<Integer>> tr) {
        System.out.println("եռանկյունիների բազմւթյուն - " + tr);
        int[][] A = new int[tr.size()][tr.size()];
        List<Integer> V=new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            for (int j = i + 1; j < A.length; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (tr.get(i).get(k) == tr.get(j).get(l)) {
                            A[i][j] = 1;
                            A[j][i] = 1;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < A.length; i++) {
            V.add(i+1);
            A[i][i] = 0;
        }
        // erankyunineri grafi matric

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                System.out.print(A[i][j] + ", ");
            }
            System.out.println();
        }

        return A;
    }

    private static List<Integer> maxIndependentPeak(int[][] A) {
        List<List<Integer>> V1 = new ArrayList<>();
        List<Integer> S=new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            List<Integer> S1 = new ArrayList<>();
            for (int j = 0; j < A.length; j++) {
                if (A[i][j] == 0) {
                    S1.add(j + 1);
                }
            }
            if(S1.size()>1)
                S1 = independentPeak(S1, A);
            if(S1.size()>S.size())
                S=S1;
            V1.add(S);
        }
        for (int i = 0; i < V1.size(); i++) {
            for (int k = i+1; k < V1.size(); k++) {
                if(V1.get(i).equals(V1.get(k))){
                    V1.remove(k);
                }
            }
        }
        return S;
    }

    private static List<Integer> independentPeak(List<Integer> S, int[][] A) {
            for (int k = 0; k < S.size(); k++) {
                int j = S.get(k) - 1;
                for (int t = 0; t < A.length; t++) {
                    if (A[j][t] == 1) {
                        for (int i = 0; i < S.size(); i++) {
                            if (S.get(i) == t + 1) {
                                S.remove(i);
                                break;
                            }
                        }
                    }
                }
        }
        return S;
    }
}