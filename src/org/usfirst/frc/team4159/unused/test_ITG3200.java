package org.usfirst.frc.team4159.unused;

//import edu.wpi.first.wpilibj.I2CJNI;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;

//public static final byte ITG3200_ADDR0 = 0b01101000;
//public static final byte ITG3200_ADDR1 = 0b01101001;

public class test_ITG3200 extends SensorBase {

	/*
	 * ************************ Register map for the ITG3200
	 ****************************/
	// public static final byte ITG_ADDR =0xD0; //0xD0 if tied low, 0xD2 if tied
	// high

	public static final byte WHO_AM_I = 0x00;
	public static final byte SMPLRT_DIV = 0x15;
	public static final byte DLPF_FS = 0x16;
	public static final byte INT_CFG = 0x17;
	public static final byte INT_STATUS = 0x1A;
	public static final byte TEMP_OUT_H = 0x1B;
	public static final byte TEMP_OUT_L = 0x1C;
	public static final byte GYRO_XOUT_H = 0x1D;
	public static final byte GYRO_XOUT_L = 0x1E;
	public static final byte GYRO_YOUT_H = 0x1F;
	public static final byte GYRO_YOUT_L = 0x20;
	public static final byte GYRO_ZOUT_H = 0x21;
	public static final byte GYRO_ZOUT_L = 0x22;
	public static final byte PWR_MGM = 0x3E;

	// Sample Rate Divider
	// Fsample = Fint / (divider + 1) where Fint is either 1kHz or 8kHz
	// Fint is set to 1kHz
	// Set divider to 9 for 100 Hz sample rate

	// DLPF, Full Scale Register Bits
	// FS_SEL must be set to 3 for proper operation
	// Set DLPF_CFG to 3 for 1kHz Fint and 42 Hz Low Pass Filter
	public static final byte DLPF_CFG_0 = (1 << 0);
	public static final byte DLPF_CFG_1 = (1 << 1);
	public static final byte DLPF_CFG_2 = (1 << 2);
	public static final byte DLPF_FS_SEL_0 = (1 << 3);
	public static final byte DLPF_FS_SEL_1 = (1 << 4);

	// Power Management Register Bits
	// Recommended to set CLK_SEL to 1,2 or 3 at startup for more stable clock
	public static final byte PWR_MGM_CLK_SEL_0 = (1 << 0);
	public static final byte PWR_MGM_CLK_SEL_1 = (1 << 1);
	public static final byte PWR_MGM_CLK_SEL_2 = (1 << 2);
	public static final byte PWR_MGM_STBY_Z = (1 << 3);
	public static final byte PWR_MGM_STBY_Y = (1 << 4);
	public static final byte PWR_MGM_STBY_X = (1 << 5);
	public static final byte PWR_MGM_SLEEP = (1 << 6);
	// public static final byte PWR_MGM_H_RESET =(1<<7);

	// Interrupt Configuration Bist
	// public static final byte INT_CFG_ACTL =(1<<7);
	public static final byte INT_CFG_OPEN = (1 << 6);
	public static final byte INT_CFG_LATCH_INT_EN = (1 << 5);
	public static final byte INT_CFG_INT_ANYRD = (1 << 4);
	public static final byte INT_CFG_ITG_RDY_EN = (1 << 2);
	public static final byte INT_CFG_RAW_RDY_EN = (1 << 0);

	private byte itg_addr;
	private I2C.Port itg_port;
	private I2C itg_I2C;

	public void test_ITG3200(I2C.Port port, byte address) {
		itg_addr = address;
		itg_port = port;
		itg_I2C = new I2C(itg_port, (int) itg_addr);
	}
	// public byte write(byte register_addr, byte value){

	// }
}