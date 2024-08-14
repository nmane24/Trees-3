// {Approach 1}
// Time Complexity : O(N * h) 
//                    ...... N is toltal number of elements in tree
//                    ...... h is height of the tree (this is for copying array at each node)
// Space Complexity : O(n* h) + h ... n is the number of nodes, h .. max size of one list ( as each list can have h elements)
                    //if we consider stack used for recursion --> again adding that h
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

/*
Leetcode : https://leetcode.com/problems/path-sum-ii/description/

Given the root of a binary tree and an integer targetSum, 
return all root-to-leaf paths where the sum of the node values in the path equals targetSum.
Each path should be returned as a list of the node values, not node references.
A root-to-leaf path is a path starting from the root and ending at any leaf node. 
A leaf is a node with no children.

Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
Output: [[5,4,11,2],[5,8,4,5]]
Explanation: There are two paths whose sum equals targetSum:
5 + 4 + 11 + 2 = 22
5 + 8 + 4 + 5 = 22



Input: root = [1,2,3], targetSum = 5
Output: []
Example 3:

Input: root = [1,2], targetSum = 0
Output: []


Code Explanation


/*
Approach 1 : Deep copy

For each recursive call to function new copy of arr is getting created while passing to function.
That means for each node, new copy is getting created. This is called as 'Deep Copy"

// Time Complexity : O(N * h) 
//                    ...... N is toltal number of elements in tree
//                    ...... h is height of the tree (this is for copying array at each node)

 Space Complexity : O(n* h) + h ... n is the number of nodes, h .. max size of one list ( as each list can have h elements)
                    //if we consider stack used for recursion --> again adding that h
*/

import java.util.ArrayList;

public class Path_sum_II {
    List<List<Integer>> result;
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        this.result = new ArrayList<>();
        helper(root, 0, targetSum, new ArrayList<>());
        return result;
    }

    private void helper(TreeNode root, int currSum, int targetSum, List<Integer> path){
        //base
        if(root == null) return;

        //action
        currSum+= root.val;
        path.add(root.val); // for every node we visit we add to the path

        if(root.left == null && root.right == null){        // if leaf node
            if(currSum == targetSum){
                result.add(path); // we add it to the main result at leaf node, then add that specific path to result
            }
        }
        
        //recursion
        // if we do not pass  new ArrayList<>(path)) and simply pass the path variable, then we get incorrect result 
        // as a shallow copy is getting formed. Below change ensures that a deep copy is getting formed and every time anew object is 
        // getting formed which copies the previous object , thereby giving correct results. 
        helper(root.left, currSum, targetSum, new ArrayList<>(path)); 
        helper(root.right, currSum, targetSum, new ArrayList<>(path));
    }
}


// Optimization approaches using backtracking 

/*
 * 
 * Basicallly not creating a deep copy is the optimization needed. 
 * We will do backtracking but without the deep copy. When this is done, still the reference to the path list is given
 * This will give incorrect result in the output even if we have added correct path as at backtracking we are removing the paths
 * everytime
 */

 public class Path_sum_II {
    List<List<Integer>> result;
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        this.result = new ArrayList<>();
        helper(root, 0, targetSum, new ArrayList<>());
        return result;
    }

    private void helper(TreeNode root, int currSum, int targetSum, List<Integer> path){
        //base
        if(root == null) return;

        //action
        currSum+= root.val;
        path.add(root.val);

        if(root.left == null && root.right == null){        // if leaf node
            if(currSum == targetSum){
                System.out.println(path);
                result.add(path);
            }
        }
        
        //recursion 
        // as we are adding path ( data structure inside a data structure is always a reference), this will cause the same path to
        // get updated everytime and thereby give incorrect results which is two empty list. That path is the same reference where at end
        // it became empty.
        helper(root.left, currSum, targetSum, path); 
        helper(root.right, currSum, targetSum, path);

        //backtracking 
        //( the action we took  is what we undo, that is adding to the list. So when the recursion is done and we go back, we will  backtrack it)
        // removing the last element ( tail element from the list) : O(1) operation
        path.remove(path.size() -1);
    }
}

// To avoid the above we create deep copy of the path when we found the value equal to target and then add to the result array list
/*
 * Time Complexity : O(n) ... and not O(n*h) as we are not creating deep copy at each node, and with backtracking we are also removing elements 
 * from the list
 * But to be very specific TC will be O(n*h) , but the deepcopy creation will happen rarely only at the leaf node when it is equal to the target.
 * Space Complexity: O(h)... height of the stack , with backtracking we are removing the elements and also path is path of output which is getting 
 * added to result and hence only O(h)
 * 
 * BackTracking - Reversing back the action that we took 
 */

public class Path_sum_II {
    List<List<Integer>> result;
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        this.result = new ArrayList<>();
        helper(root, 0, targetSum, new ArrayList<>());
        return result;
    }

    private void helper(TreeNode root, int currSum, int targetSum, List<Integer> path){
        //base
        if(root == null) return;

        //action
        currSum+= root.val;
        path.add(root.val);
        
        // processing the root
        if(root.left == null && root.right == null){        // if leaf node
            if(currSum == targetSum){
                System.out.println(path);
                result.add(new ArrayList<>(path)); // change from previous one , i.e. created only deep copy here
            }
        }
        
        //recursion 
        // as we are adding path ( data structure inside a data structure is always a reference), this will cause the same path to
        // get updated everytime and thereby give incorrect results which is two empty list. That path is the same reference where at end
        // it became empty.
        helper(root.left, currSum, targetSum, path); 
        helper(root.right, currSum, targetSum, path);

        //backtracking
        // removing the last element ( tail element from the list) : O(1) operation
        path.remove(path.size() -1);
    }
}

// if we change the order also then it will run and give expected result. 
// Inorder / postorder traversal below will also give expected result

public class Path_sum_II {
    List<List<Integer>> result;
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        this.result = new ArrayList<>();
        helper(root, 0, targetSum, new ArrayList<>());
        return result;
    }

    private void helper(TreeNode root, int currSum, int targetSum, List<Integer> path){
        //base
        if(root == null) return;

        //action
        currSum+= root.val;
        path.add(root.val);
        
        //recursion 
        helper(root.left, currSum, targetSum, path); 
        // inorder , processing the root
        if(root.left == null && root.right == null){        // if leaf node
            if(currSum == targetSum){
                System.out.println(path);
                result.add(new ArrayList<>(path)); // change from previous one , i.e. created only deep copy here
            }
        }
        helper(root.right, currSum, targetSum, path);



        /*
         * 
         *  //recursion 
                helper(root.left, currSum, targetSum, path); 
                 helper(root.right, currSum, targetSum, path);
            // postorder
                if(root.left == null && root.right == null){        // if leaf node
                    if(currSum == targetSum){
                        System.out.println(path);
                        result.add(new ArrayList<>(path)); // change from previous one , i.e. created only deep copy here
                    }
                }
         */

        //backtracking
        // removing the last element ( tail element from the list) : O(1) operation
        path.remove(path.size() -1);
    }
}