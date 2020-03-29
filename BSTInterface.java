package col106.assignment3.BST ;
public interface BSTInterface <T extends Comparable, E extends Comparable>{
/**
* Insert an element using the "key" as the key and the
corresponding value .
* Please note that value is a generic type and it can be
anything .
*
* @param key
* @param value
*/
void insert ( T key , E value ) ;
 /**
 * Update to new value using the key.
 *
 * @param key
 * @param value
 */
 void update(T key, E value);
 /**
 *
 * Delete element using key
 * @param key
 */
 void delete ( T key ) ;
 /**
 *
 * Print the keys according to level order traversal of the BST
 * 
 */
 void printBST () ;
}