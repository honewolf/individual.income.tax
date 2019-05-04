/**
 * 
 */
package individual.income.tax;

// ��Stream�Կ���̨������������ж�ȡ�ʹ���
import java.io.InputStreamReader;
import java.io.BufferedReader;

// �����������쳣�����������ʵ���ϻ����޴����
import java.io.IOException;


// ��ҵ������Ҫ�ô���ֵ���ͱ�֤����
import java.math.BigDecimal;

/**
 * rain��ʦ ���õ���ҵ
 * ����һ����������˰�ļ��㹤�ߣ����㹫ʽ�ο���ip138��˰���㹤��
 * ��http://www.ip138.com/geshui/���еĹ�ʽ����ϸ���ݼ���Ӧ���� 
 *  IO�Ĵ������ڼ����е���ֵת�����ο���һλ�����ѵ�����
 * 
 * @author honewolf
 * @version  v0.02
 * @date 2019-05-04 ����9:39
 */
public class MainCalculator {

	/**
	 * ��˰���������㹫ʽ:
	 * Ӧ��˰���ö� = ���������� �� ������ᱣ�շ� �� ������(Ĭ��3500Ԫ)
	 * Ӧ��˰�� = Ӧ��˰���ö� �� ˰�� �� ����۳���
	 * ˵����ʵ�ʵ�ǰ��˰���������ϵ���5000Ԫ
	 * 
	 *           ��˰������˰�ʱ�
	 * ����   	����Ӧ��˰���ö� 	    ˰��(%) ����۳��� 
	 *  1 	������1,500Ԫ 	                 3 	      0
	 *  2 	����1,500Ԫ��4,500Ԫ�Ĳ��� 	    10 	    105 
	 *  3 	����4,500Ԫ��9,000Ԫ�Ĳ��� 	    20 	    555
	 *  4 	����9,000Ԫ��35,000Ԫ�Ĳ��� 	25 	  1,005
	 *  5   ����35,000Ԫ��55,000Ԫ�Ĳ��� 	30 	  2,755
	 *  6 	����55,000Ԫ��80,000Ԫ�Ĳ��� 	35 	  5,505
	 *  7 	����80,000Ԫ�Ĳ��� 	            45 	 13,505
	 *  
	 * @param income:��������,base:������
	 * @return tax: ��˰��
	 * 
	 * ע�⣺������Ĭ�ϸ�����ᱣ�շ�Ϊ0
	 * 
	 */
	private static BigDecimal calculate(BigDecimal income, BigDecimal base){
		
		
		// Ӧ��˰���ö� = ���������� �� ������ᱣ�շ� �� ������
		// ����  ������ᱣ�շ� = 0
		BigDecimal taxableIncome = income.subtract(base);
		
		// ��˰�� ��ʼֵΪ0
		BigDecimal tax = new BigDecimal("0");
		
		// �߼�����Ӧ��˰���ö���ٽ�ֵ
		BigDecimal INCOME_LEVEL_SEVEN = new BigDecimal("80000");
		BigDecimal   INCOME_LEVEL_SIX = new BigDecimal("55000");
		BigDecimal  INCOME_LEVEL_FIVE = new BigDecimal("35000");
		BigDecimal  INCOME_LEVEL_FOUR = new BigDecimal("9000");
		BigDecimal INCOME_LEVEL_THREE = new BigDecimal("4500");
		BigDecimal   INCOME_LEVEL_TWO = new BigDecimal("1500");
		BigDecimal   INCOME_LEVEL_ONE = new BigDecimal("0");
		
		
		if (taxableIncome.compareTo(INCOME_LEVEL_SEVEN) == 1) {
			// Ӧ��˰���ö��80,000Ԫ
			//tax = taxableIncome * 0.45 - 13505;
			tax = taxCalc(taxableIncome, "0.45", "13505");
			return tax;
		} 
		if (taxableIncome.compareTo(INCOME_LEVEL_SIX) == 1) {
			//  Ӧ��˰���ö�Ϊ55,000Ԫ��80,000Ԫ
			//tax = taxableIncome * 0.35 - 5505;
			tax = taxCalc(taxableIncome, "0.35", "5505");
			return tax;
		} 
		if (taxableIncome.compareTo(INCOME_LEVEL_FIVE) == 1) {
			// Ӧ��˰���ö�Ϊ35,000Ԫ��55,000Ԫ 
			//tax = taxableIncome * 0.30 - 2755;
			tax = taxCalc(taxableIncome, "0.30", "2755");
			return tax;
		} 
		if (taxableIncome.compareTo(INCOME_LEVEL_FOUR) == 1){
			// Ӧ��˰���ö�Ϊ9,000Ԫ��35,000Ԫ
			//tax = taxableIncome * 0.25 - 1005;
			tax = taxCalc(taxableIncome, "0.25", "1005");
			return tax;
		}
		if (taxableIncome.compareTo(INCOME_LEVEL_THREE) == 1) {
			// Ӧ��˰���ö�Ϊ4,500Ԫ��9,000Ԫ
			//tax = taxableIncome * 0.20 - 555;
			tax = taxCalc(taxableIncome, "0.20", "555");
			return tax;
		}
		if (taxableIncome.compareTo(INCOME_LEVEL_TWO) == 1) {
			// Ӧ��˰���ö�Ϊ1,500Ԫ��4,500Ԫ
			//tax = taxableIncome * 0.10 - 105;
			tax = taxCalc(taxableIncome, "0.10", "105");
			return tax;
		}
		if (taxableIncome.compareTo(INCOME_LEVEL_ONE) == 1) {
			// Ӧ��˰���ö�Ϊ0Ԫ��1,500Ԫ
			//tax = taxableIncome * 0.03 - 0;
			tax = taxCalc(taxableIncome, "0.03", "0");
		    return tax;
		}
	    	
		// �絽������δreturn
		// ��˵��������������㣬Ӧ��˰��Ϊ0��
	    // tax ��ֵΪ0
	    return tax;
				
	}
	
	/**
	 * calculate��һ�����׷�����
	 * 
	 * ������ʹ��BigDecimalʱ��������ʽ�Ĺ��̣�
	 * Ӧ��˰�� = Ӧ��˰���ö� �� ˰�� �� ����۳���
	 * 
	 * @param taxableIncome:Ӧ˰���룬ApplicableTaxRate: ���õ�˰� 
	 * @param quickDeduction: ���ٿ۳���
	 * @return tax ������ʽ�ļ�����
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
	 * ���Խ��б���������˰�������Ĳ˵���ӡ��IO����
	 * 
	 * 
	 * @throws IOException
	 */
	private static void controlMenu() throws IOException {
		
		// System.in��ȡ��׼�����byte����ͨ��InputStreamReaderת��Ϊ�ַ���
		InputStreamReader isr = new InputStreamReader(System.in);
		// ��BufferedReader�еĹ��� ����ȡ����ַ�����Ϊ��̴�����׼��
		BufferedReader br = new BufferedReader(isr);

		// ������,Ĭ��Ϊ 3500�ɽ����޸�
		BigDecimal base = new BigDecimal("3500"); 
		
		while (true) {
			System.out.print("\n" +
					"1. ��������˰\n" + 
					"2. �޸�������\n" + 
					"3. �˳�\n" + 
					"\n" + 
					"��ѡ��1-3����");

			String line = br.readLine().trim(); // ��br�ж�ȡ��һ�в�ɾȥǰ��ո�
			
			// ������벢�ǣ�1-3��֮������֣�����ʾ��������
			if (false == ("1".equals(line) || "2".equals(line) || "3".equals(line))){
				System.out.println("\n��Ч���룡\n" +
						           "�밴��ʾ�ڣ�1-3���н���ѡ��\n");
				continue;
			}
			
			if ( "3".equals(line) ) {
				return;
			} else if ("2".equals(line)) {
				// �޸�������
				System.out.println("��ǰ������Ϊ " + base);
				
				while (true) {
					System.out.print("�������µ������㣺");
					line = br.readLine().trim();
					
					try {
						base = new BigDecimal(line);	
						break;
					} catch ( NumberFormatException ex) {
						System.out.println("��Ч���룡\n");
					}
				}
				
				System.out.println("�������Ѿ��޸�Ϊ" + base);				
			} else if ( "1".equals(line) ) {
				// ��������˰
				BigDecimal income;

				while (true) {
					System.out.print("�����������룺");
					line = br.readLine().trim();
					
					try {
						income = new BigDecimal(line); 	
						break;
					} catch ( NumberFormatException ex) {
						System.out.println("��Ч���룡\n");
					}
				}
				
				System.out.format("Ӧ������˰Ϊ��" + calculate(income, base));
			}			
		}
	}
		
	
	public static void main(String[] args) throws IOException {
		
			controlMenu();
	}

	


}
