

/**
 * Class implementing a basic cache with a configurable size and associativity.
 * 
 * The cache is backed-up by a "Memory" object which actually stores stores the values -- on a cache miss, the Memory should be accessed.
 * 
 */
public class Cache implements ReadOnlyCache
{
	private Memory m_memory;
	

	/**
	 * Constructor
	 * @param memory - An object implementing the Memory functionality.  This should be accessed on a cache miss
	 * @param blockCount - The number of blocks in the cache.
	 * @param bytesPerBlock - The number of bytes per block in the cache.
	 * @param associativity - The associativity of the cache.  1 means direct mapped, and a value of "blockCount" means fully-associative.
	 */
	
	// Variables needed 
	public byte[][] byteArray;
	public int blockCounter;
	public int[] blockArray; 
	public boolean[] boolArray;

	public int bytes;
	
	public Cache(Memory memory, int blockCount, int bytesPerBlock, int associativity)
	{
		
		
		// Assign values for use later
		byteArray = new byte[blockCount][bytesPerBlock];
		blockCounter = blockCount;
		blockArray = new int[blockCounter]; 
		bytes= bytesPerBlock;
		
		m_memory = memory;
		boolArray = new boolean[blockCounter];
	}

	/**
	 * Method to retrieve the value of the specified memory location.
	 * 
	 * @param address - The address of the byte to be retrieved.
	 * @return The value at the specified address.
	 */
	public byte load(int address)
	{
		// fetch using caching logic.  This implementation
		// does not do any caching, it just immediately
		// accesses memory, and is not correct.

		blockArray[(address / bytes) % blockCounter] = address / (bytes*blockCounter);
		if( ((address / (bytes*blockCounter)) == blockArray[(address / bytes) % blockCounter] && boolArray[(address / bytes) % blockCounter])){
			
			return byteArray[(address / bytes) % blockCounter][address % bytes];
		
		}
			
			boolArray[(address / bytes) % blockCounter]=true;
			byteArray[(address / bytes) % blockCounter]= m_memory.read(bytes*(address/bytes ), bytes);
			
			return byteArray[(address / bytes) % blockCounter][address % bytes];
		
	}
	