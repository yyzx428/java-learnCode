package Algorithm;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GAKnapsack {

    private Random random = null; //随机数生成器

    private ArrayList<BigDecimal> weight = null; //物品重量
    private int len; // 染色体长度

    private double capacity; //背包容量
    private int scale; //种群规模
    private int maxgen; //最大代数
    private float irate; //交叉率（所有的个体都需要相互交叉的，这里的交叉率指交叉时每位交叉发生交叉的可能性）
    private float arate1; //变异率（某个个体发生变异的可能性）
    private float arate2; //对于确定发生变异的个体每位发生变异的可能性
    private File data = null; //物品重量和物品价值的数据文件

    private boolean[][] population = null; //上一代种群
    private ArrayList<BigDecimal> fitness = null; //种群的适应度

    private ArrayList<BigDecimal> fitnessPer = null; //交叉的选择概率

    private BigDecimal bestFitness = new BigDecimal("0.0000"); //最优个体的价值
    private boolean[] bestUnit = null; //最优个体的物品取舍策略

    /**
     * @param capacity : 背包容量
     * @param scale    ： 种群规模
     * @param maxgen   ： 最大代数
     * @param irate    ： 交叉率（所有的个体都需要相互交叉的，这里的交叉率指交叉时每位交叉发生交叉的可能性）
     * @param arate1   ：变异率（某个个体发生变异的可能性）
     * @param arate2   ：对于确定发生变异的个体每位发生变异的可能性
     * @param file     : 物品重量和物品价值的数据文件
     */
    public GAKnapsack(double capacity, int scale, int maxgen, float irate, float arate1, float arate2, File data) {
        this.capacity = capacity;
        this.scale = scale;
        this.maxgen = maxgen;
        this.irate = irate;
        this.arate1 = arate1;
        this.arate2 = arate2;
        this.data = data;
        random = new Random(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        File data = new File("C:\\Users\\29410\\Desktop\\amount.txt");
        //背包容量
        //种群规模
        //最大代数
        //交叉率（所有的个体都需要相互交叉的，这里的交叉率指交叉时每位交叉发生交叉的可能性）
        //变异率（某个个体发生变异的可能性）
        //对于确定发生变异的个体每位发生变异的可能性
        //物品重量和物品价值的数据文件
        GAKnapsack gaKnapsack = new GAKnapsack(50, 50, 200, 0.5f, 0.05f, 0.1f, data);
        gaKnapsack.solve();
    }

    //读取物品重量和物品价值数据
    private void readDate() {
        weight = Reader.read(data);
        //profit = (float[])tmp.get(1);
        len = weight.size();
    }

    //初始化初始种群
    private void initPopulation() {
        fitness = new ArrayList<>(scale);
        population = new boolean[scale][len];
        //考虑到随机生成的初始化种群效果有可能不好，这里对于种群的初始化作了一定的优化
        //对于每一个个体，先随机一个容量值（0.5 capacity 至 1.5 capacity）
        //然后随机相应的物品到该个体中，直到超过上面随机的容量
        for (int i = 0; i < scale; i++) {
            //float tmp = (float) (0.5 + Math.random()) * capacity;
            double tmp = capacity;
            int count = 0; //防止初始化耗费太多计算资源
            for (double j = 0; j < tmp; ) {
                int k = random.nextInt(len);
                if (population[i][k]) {
                    if (count == 3) {
                        break;
                    }
                    count++;
                } else {
                    population[i][k] = true;
                    j += weight.get(k).doubleValue();
                    count = 0;
                }
            }
        }
    }

    //计算一个个体的适应度
    private BigDecimal evaluate(boolean[] unit) {
        BigDecimal weightSum = new BigDecimal("0.0000");
        for (int i = 0; i < unit.length; i++) {
            if (unit[i]) {
                weightSum = weightSum.add(weight.get(i));
            }
        }
        if (weightSum.compareTo(new BigDecimal(capacity)) > 0) {
            //该个体的对应的所有物品的重量超过了背包的容量
            if (weightSum.subtract(new BigDecimal(capacity)).doubleValue() < 10) {
                return weightSum;
            }

            return new BigDecimal("0.0000");
        } else {
            return weightSum;
        }
    }

    //计算种群所有个体的适应度
    private void calcFitness() {
        for (int i = 0; i < scale; i++) {
            fitness.add(i, evaluate(population[i]));
        }
    }

    //记录最优个体
    private void recBest() {
        for (int i = 0; i < scale; i++) {
            BigDecimal target = new BigDecimal(capacity);
            if (fitness.get(i).subtract(target).abs().compareTo(
                    bestFitness.subtract(target).abs()) < 0) {
                bestFitness = new BigDecimal(fitness.get(i).doubleValue());
                bestUnit = new boolean[len];
                for (int j = 0; j < len; j++) {
                    bestUnit[j] = population[i][j];
                }
            }
        }
    }

    //种群个体选择
    //选择策略：适应度前10%的个体带到下一次循环中，然后在（随机生成10%的个体 + 剩下的90%个体）中随机取90%出来
    private void select() {
        SortFitness[] sortFitness = new SortFitness[scale];
        for (int i = 0; i < scale; i++) {
            sortFitness[i] = new SortFitness();
            sortFitness[i].index = i;
            sortFitness[i].fitness = fitness.get(i);
        }
        Arrays.sort(sortFitness);

        boolean[][] tmpPopulation = new boolean[scale][len];

        //保留前10%的个体
        int reserve = (int) (scale * 0.1);
        for (int i = 0; i < reserve; i++) {
            for (int j = 0; j < len; j++) {
                tmpPopulation[i][j] = population[sortFitness[i].index][j];
            }
            //将加入后的个体随机化
            for (int j = 0; j < len; j++) {
                population[sortFitness[i].index][j] = false;
            }
            //float tmpc = (float) (0.5 + Math.random()) * capacity;
            double tmpc = capacity;
            int count = 0;
            for (double j = 0; j < tmpc; ) {
                int k = random.nextInt(len);
                if (population[sortFitness[i].index][k]) {
                    if (count == 3) {
                        break;
                    }
                    count++;
                    continue;
                } else {
                    population[sortFitness[i].index][k] = true;
                    j += weight.get(k).doubleValue();
                    count = 0;
                }
            }//
        }

        //再随机90%的个体出来
        int num = scale;

        for (int i = reserve; i < scale; i++) {
            int selectid = (int) (num * Math.random());
            for (int j = 0; j < len; j++) {
                tmpPopulation[i][j] = population[selectid][j];
            }
            num--;
        }
        population = tmpPopulation;
    }

    //进行交叉(单点交叉)
    private void intersect() {
        for (int i = 0; i < scale; i += 2)
            for (int j = 0; j < len; j++) {
                if (Math.random() < irate) {
                    for (; j < len; j++) {
                        boolean tmp = population[i][j];
                        population[i][j] = population[i + 1][j];
                        population[i + 1][j] = tmp;
                    }
                    break;
                }
            }
    }

    //变异
    private void aberra() {
        for (int i = 0; i < scale; i++) {
            if (Math.random() > arate1) {
                continue;
            }
            for (int j = 0; j < len; j++) {
                if (Math.random() < arate2) {
                    // population[i][j] = Math.random() > 0.5 ? true : false;
                    population[i][j] = !population[i][j];
                }
            }
        }
    }

    //遗传算法
    public void solve() {
        readDate();
        initPopulation();
        for (int i = 0; i < maxgen; i++) {
            //计算种群适应度值
            calcFitness();
            //记录最优个体
            recBest();
            //进行种群选择
            select();
            //进行交叉
            intersect();
            //发生变异
            aberra();
            System.out.println("第" + (i + 1) + "代最优解:" + bestFitness.doubleValue());
        }

        BigDecimal totalWeight = new BigDecimal("0.0000");
        for (int i = 0; i < bestUnit.length; i++) {
            if (bestUnit[i]) {
                totalWeight = totalWeight.add(weight.get(i));
                System.out.print(weight.get(i).doubleValue() + "+");
            }
        }
        System.out.println();
        System.out.println(bestUnit.length);
        System.out.println("total profit:" + bestFitness.doubleValue());
        System.out.println("total weight:" + totalWeight);
    }

    class SortFitness implements Comparable<SortFitness> {
        int index;
        BigDecimal fitness;

        public int compareTo(SortFitness c) {
            BigDecimal cfitness = c.fitness;
            if (fitness.compareTo(cfitness) > 0) {
                return -1;
            } else if (fitness.compareTo(cfitness) < 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

