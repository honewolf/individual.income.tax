/**
 * 
 */
package individual.income.tax;

// 用Stream对控制台的输入输出进行读取和处理
import java.io.InputStreamReader;
import java.io.BufferedReader;

// 用来处理下异常的输入输出，实际上还是无处理的
import java.io.IOException;


// 商业计算需要用大数值类型保证精度
import java.math.BigDecimal;

/**
 * rain老师 布置的作业
 * 这是一个个人所得税的计算工具，计算公式参考了ip138个税计算工具
 * “http://www.ip138.com/geshui/”中的公式，详细内容见对应方法 
 *  IO的处理、金融计算中的数值转换，参考了一位老朋友的例程
 * 
 * @author honewolf
 * @version  v0.02
 * @date 2019-05-04 上午9:39
 */
public class MainCalculator {

	/**
	 * 个税计算器计算公式:
	 * 应纳税所得额 = 工资收入金额 － 各项社会保险费 － 起征点(默认3500元)
	 * 应纳税额 = 应纳税所得额 × 税率 － 速算扣除数
	 * 说明：实际当前个税起征点已上调至5000元
	 * 
	 *           个税计算器税率表
	 * 级数   	当月应纳税所得额 	    税率(%) 速算扣除数 
	 *  1 	不超过1,500元 	                 3 	      0
	 *  2 	超过1,500元至4,500元的部分 	    10 	    105 
	 *  3 	超过4,500元至9,000元的部分 	    20 	    555
	 *  4 	超过9,000元至35,000元的部分 	25 	  1,005
	 *  5   超过35,000元至55,000元的部分 	30 	  2,755
	 *  6 	超过55,000元至80,000元的部分 	35 	  5,505
	 *  7 	超过80,000元的部分 	            45 	 13,505
	 *  
	 * @param income:工资收入,base:启征点
	 * @return tax: 纳税额
	 * 
	 * 注意：本方法默认各项社会保险费为0
	 * 
	 */
	private static BigDecimal calculate(BigDecimal income, BigDecimal base){
		
		
		// 应纳税所得额 = 工资收入金额 － 各项社会保险费 － 起征点
		// 假设  各项社会保险费 = 0
		BigDecimal taxableIncome = income.subtract(base);
		
		// 纳税额 初始值为0
		BigDecimal tax = new BigDecimal("0");
		
		// 七级当月应纳税所得额的临界值
		BigDecimal INCOME_LEVEL_SEVEN = new BigDecimal("80000");
		BigDecimal   INCOME_LEVEL_SIX = new BigDecimal("55000");
		BigDecimal  INCOME_LEVEL_FIVE = new BigDecimal("35000");
		BigDecimal  INCOME_LEVEL_FOUR = new BigDecimal("9000");
		BigDecimal INCOME_LEVEL_THREE = new BigDecimal("4500");
		BigDecimal   INCOME_LEVEL_TWO = new BigDecimal("1500");
		BigDecimal   INCOME_LEVEL_ONE = new BigDecimal("0");
		
		
		if (taxableIncome.compareTo(INCOME_LEVEL_SEVEN) == 1) {
			// 应纳税所得额超过80,000元
			//tax = taxableIncome * 0.45 - 13505;
			tax = taxCalc(taxableIncome, "0.45", "13505");
			return tax;
		} 
		if (taxableIncome.compareTo(INCOME_LEVEL_SIX) == 1) {
			//  应纳税所得额为55,000元至80,000元
			//tax = taxableIncome * 0.35 - 5505;
			tax = taxCalc(taxableIncome, "0.35", "5505");
			return tax;
		} 
		if (taxableIncome.compareTo(INCOME_LEVEL_FIVE) == 1) {
			// 应纳税所得额为35,000元至55,000元 
			//tax = taxableIncome * 0.30 - 2755;
			tax = taxCalc(taxableIncome, "0.30", "2755");
			return tax;
		} 
		if (taxableIncome.compareTo(INCOME_LEVEL_FOUR) == 1){
			// 应纳税所得额为9,000元至35,000元
			//tax = taxableIncome * 0.25 - 1005;
			tax = taxCalc(taxableIncome, "0.25", "1005");
			return tax;
		}
		if (taxableIncome.compareTo(INCOME_LEVEL_THREE) == 1) {
			// 应纳税所得额为4,500元至9,000元
			//tax = taxableIncome * 0.20 - 555;
			tax = taxCalc(taxableIncome, "0.20", "555");
			return tax;
		}
		if (taxableIncome.compareTo(INCOME_LEVEL_TWO) == 1) {
			// 应纳税所得额为1,500元至4,500元
			//tax = taxableIncome * 0.10 - 105;
			tax = taxCalc(taxableIncome, "0.10", "105");
			return tax;
		}
		if (taxableIncome.compareTo(INCOME_LEVEL_ONE) == 1) {
			// 应纳税所得额为0元至1,500元
			//tax = taxableIncome * 0.03 - 0;
			tax = taxCalc(taxableIncome, "0.03", "0");
		    return tax;
		}
	    	
		// 如到这里仍未return
		// 则说明收入低于起征点，应纳税额为0；
	    // tax 初值为0
	    return tax;
				
	}
	
	/**
	 * calculate的一个配套方法，
	 * 
	 * 用来简化使用BigDecimal时打以下算式的过程：
	 * 应纳税额 = 应纳税所得额 × 税率 － 速算扣除数
	 * 
	 * @param taxableIncome:应税收入，ApplicableTaxRate: 适用的税额， 
	 * @param quickDeduction: 快速扣除数
	 * @return tax 上述算式的计算结果
	 * 
	 */
	private static BigDecimal taxCalc(BigDecimal taxableIncome,
			                           String     ApplicableTaxRate,
			                           String     quickDeduction) {
		
		return (taxableIncome.multiply(new BigDecimal(ApplicableTaxRate))).
				subtract(new BigDecimal(quickDeduction));
		
	}
	
	/**
	 * 
	 * 用以进行本个人所得税计算器的菜单打印和IO控制
	 * 
	 * 
	 * @throws IOException
	 */
	private static void controlMenu() throws IOException {
		
		// System.in读取标准输入的byte流，通过InputStreamReader转化为字符流
		InputStreamReader isr = new InputStreamReader(System.in);
		// 用BufferedReader中的工具 来读取这个字符流，为后继处理做准备
		BufferedReader br = new BufferedReader(isr);

		// 起征点,默认为 3500可进行修改
		BigDecimal base = new BigDecimal("3500"); 
		
		while (true) {
			System.out.print("\n" +
					"1. 计算所得税\n" + 
					"2. 修改起征点\n" + 
					"3. 退出\n" + 
					"\n" + 
					"请选择（1-3）：");

			String line = br.readLine().trim(); // 在br中读取下一行并删去前后空格
			
			// 如果输入并非（1-3）之间的数字，则提示重新输入
			if (false == ("1".equals(line) || "2".equals(line) || "3".equals(line))){
				System.out.println("\n无效输入！\n" +
						           "请按提示在（1-3）中进行选择！\n");
				continue;
			}
			
			if ( "3".equals(line) ) {
				return;
			} else if ("2".equals(line)) {
				// 修改起征点
				System.out.println("当前起征点为 " + base);
				
				while (true) {
					System.out.print("请输入新的起征点：");
					line = br.readLine().trim();
					
					try {
						base = new BigDecimal(line);	
						break;
					} catch ( NumberFormatException ex) {
						System.out.println("无效输入！\n");
					}
				}
				
				System.out.println("起征点已经修改为" + base);				
			} else if ( "1".equals(line) ) {
				// 计算所得税
				BigDecimal income;

				while (true) {
					System.out.print("请输入月收入：");
					line = br.readLine().trim();
					
					try {
						income = new BigDecimal(line); 	
						break;
					} catch ( NumberFormatException ex) {
						System.out.println("无效输入！\n");
					}
				}
				
				System.out.format("应交所得税为：" + calculate(income, base));
			}			
		}
	}
		
	
	public static void main(String[] args) throws IOException {
		
			controlMenu();
	}

	


}
