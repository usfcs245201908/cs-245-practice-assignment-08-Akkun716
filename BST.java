import java.util.*;

public class BST<T>
{
    private Node root, current, find;
    String output;

    public boolean find (Comparable<T> item)
    {
        //Current will be the node we follow
        current = root;
        //In case a root has not been established yet
        while(current != null)
        {
            if(current.getData().compareTo(item) == 0)
            {
                //Find is a Node variable that will be used to find the node needed
                // to be deleted the delete function
                this.find = current;
                return true;
            }
            //If the item is less than current's data
            else if(current.getData().compareTo(item) > 0)
            {
                current = current.getLeft();
            }
            //Inversely if item is greater than current's data
            else
            {
                current = current.getRight();
            }
        }
        //If the data could not be located
        this.find = null;
        return false;
    }

    public void insert(Comparable<T> item)
    {
        current = root;
        boolean r = false;
        Node previous = root;
        boolean found = false;
        //If the root doesn't currently hold any data, "insert" the item into root
        if(root == null)
        {
            root = new Node();
            root.setData(item);
        }
        else
        {
            while(current != null && found == false)
            {
//                System.out.println(current);
                //Since root can't have a previous, only nodes after root can have a previous
                if(current != root)
                {
                    current.setPrev(previous);
                }

                if(current.getData().compareTo(item) == 0)
                {
                    //Found will indicate that the new node will be a duplicate
                    found = true;
                }
                else if(current.getData().compareTo(item) > 0)
                {
                    //Previous will indicate the parent of the child we are accessing
                    previous = current;
                    current = current.getLeft();
                    //These booleans indicate which direction the new child should be descended
                    r = false;
                }
                else
                {
                    previous = current;
                    current = current.getRight();
                    r = true;
                }
            }

            Node newNode = new Node();
            newNode.setData(item);
            //This will create a duplicate node set to the left of the existing node
            if(found)
            {
                newNode.setLeft(current.getLeft());
                current.setLeft(newNode);
            }
            else if(current == null)
            {
                //If the previous node looked to the right
                if(r)
                {
                    previous.setRight(newNode);
                    newNode.setPrev(previous);
                }
                //If t
                else
                {
                    previous.setLeft(newNode);
                    newNode.setPrev(previous);
                }
            }

//            System.out.println(newNode + "\n");
        }
    }

    public Node delete(Comparable item)
    {

        Node temp = null;
        boolean childExist = false, l = false, r = false;

        find(item);
        //This sets the node that will be deleted from the tree
        Node delt = find;
//        System.out.println("delt = " + delt + "\n");

        if(root == null)
        {
            System.out.println("[No nodes in tree]");
        }
        else if(delt == null)
        {
            System.out.println("[This item does not exist in tree]");
        }
        else
        {
//            System.out.println("delt.right = " + delt.getRight());
//            System.out.println("delt.left = " + delt.getLeft());
            //If there is no right child, the left child will be promoted
            if(delt.getRight() == null)
            {
                temp = delt.getLeft();
//                System.out.println("(left) temp = " + temp + "\n");

                if(delt != root)
                {
                    //If the node to be deleted is a descendant of the left child
                    if(delt.getPrev().getRight() == delt)
                    {
                        delt.getPrev().setRight(temp);
//                        System.out.println("delt prev's right = " + temp + "\n");
                    }
                    else
                    {
                        delt.getPrev().setLeft(temp);
//                        System.out.println("delt prev's left = " + temp + "\n");
                    }

                    if(temp != null)
                    {
                        temp.setPrev(delt.getPrev());
                    }
                }
            }
            //If there is a right child, we want to find the least child to it (if one exists)
            else
            {
                temp = delt.getRight();
//                System.out.println("(right) temp = " + temp + "\n");
                //This moves down all left children to find the least child
                while(temp.getLeft() != null)
                {
                    temp = temp.getLeft();
                    childExist = true;
//                    System.out.println("new temp = " + temp);
                }
                //Since temp will be moved, we want to set prev's left to null
                temp.getPrev().setLeft(null);
                //If there are right children to the temp, we will replace where
                // temp used to be with its child
                if(temp.getRight() != null)
                {
                    temp.getPrev().setLeft(temp.getRight());
                }

                if(delt != root)
                {
                    if(delt.getPrev().getRight() == delt)
                    {
                        delt.getPrev().setRight(temp);
//                        System.out.println("delt prev's right = " + temp + "\n");
                    }
                    else
                    {
                        delt.getPrev().setLeft(temp);
//                        System.out.println("delt prev's left = " + temp + "\n");
                    }

                    temp.setPrev(delt.getPrev());
                }

                temp.setLeft(delt.getLeft());
                //If a child doesn't exist we don't want to set the temp's right as itself
                if(childExist)
                {
                    temp.setRight(delt.getRight());
                }
//                System.out.println("temps left = " + temp.getLeft());
//                System.out.println("temps right = " + temp.getRight() + "\n");
            }

            if(delt == root)
            {
                root = temp;
                if(temp != null)
                {
                    temp.setPrev(null);
                }
//                System.out.println("prev root = " + delt);
//                System.out.println("new root = " + temp);
            }
        }

        return delt;
    }

    public void print()
    {
        if(root != null)
        {
            print(root);
        }
    }

    private void print(Node node)
    {
        if(node.getLeft() != null)
        {
            print(node.getLeft());
        }

        System.out.println(node);

        if(node.getRight() != null)
        {
            print(node.getRight());
        }
    }

    public class Node<T>
    {

        private Comparable data;
        private Node left;
        private Node right;
        private Node prev;

        void setData(Comparable item)
        {
            data = item;
        }

        public Comparable<T> getData()
        {
            return data;
        }

        public void setLeft(Node node)
        {
            left = node;
        }

        public Node getLeft()
        {
            return left;
        }

        public void setRight(Node node)
        {
            right = node;
        }

        public Node getRight()
        {
            return right;
        }

        public void setPrev(Node node)
        {
            prev = node;
        }

        public Node getPrev()
        {
            return prev;
        }

        public String toString()
        {
            return "" + data;
        }
    }
}
