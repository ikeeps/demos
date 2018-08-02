package javaType;

public class LongBitOps {
  private long campaignList;
  
  /**
   * 一个礼包是否可以领取
   * @param idx
   * @return
   */
  public boolean getCampaignAvailable(int idx) {
    return (!LongBitOps.get(campaignList, 2 * idx)) 
        && LongBitOps.get(campaignList, 2 * idx - 1);
  }
  
  /**
   * 领取完后不再激活，再次激活礼包，也不可以领取
   * @param idx
   */
  public void setCampaignDisable(int idx) {
    long re = LongBitOps.set(campaignList, 2 * idx);
    campaignList = re;
  }
  
  /**
   * 激活礼包，可以领取
   * @param idx
   */
  public void setCampaignEnable(int idx) {
    long re = LongBitOps.set(campaignList, 2 * idx - 1);
    campaignList = re;
  }
  
  public static final boolean get(long value, int idx) {
    int n_idx = idx & 0x3f;
    return (value & 1L << n_idx) != 0;
  }
  
  public static final long set(long value, int idx) {
    int n_idx = idx & 0x3f;
    return value | (1L << n_idx);
  }
  
  public static final long clear(long value, int idx) {
    int n_idx = idx & 0x3f;
    return value & ~(1L << n_idx);
  }
  
  public static void main(String[] args) {
    LongBitOps campaign = new LongBitOps();
    for(int i = 1; i < 9; i++) {
      campaign.setCampaignEnable(i);
      System.out.println(campaign.getCampaignAvailable(i));
      campaign.setCampaignDisable(i);
      System.out.println(campaign.getCampaignAvailable(i));
      campaign.setCampaignEnable(i);
      System.out.println(campaign.getCampaignAvailable(i));
      System.out.println(Long.toBinaryString(campaign.campaignList));
    }
    
    System.out.println(Long.MAX_VALUE);
  }
}
