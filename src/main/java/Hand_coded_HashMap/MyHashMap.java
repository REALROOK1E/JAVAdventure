package Hand_coded_HashMap;

/**
 * @author: ZeKai
 * @date: 2025/6/21
 * @description:
 **/
public class MyHashMap<K,V> {

    Node<K,V>[] arr=new Node[16];
    
    int size=0;

    double factor=0.75;

    public V get(K key) {
        int index=indexof(key);
        //先拿到目标，然后随你处理
        Node<K,V> node= arr[index];
        //没有就加上
        if(node==null)  return null;
        //有就找链表
        while(node!=null){
            if(node.key.equals(key)) return node.value;
            node=node.next;
        }
        return null;
    }

//所以我实际上就是，在put时候先计算一个下标，
//然后把数组里下标对应元素拿出来看，如果空的，就直接把我的kv放进去，
//如果有，那么就在链表上一个个的找，看哪一个和我的key一样，就把哪个的vallue改成我的
    public V put(K key, V value) {
        int index=indexof(key);
        //先拿到目标，然后随你处理
        Node<K,V> node= arr[index];
        //没有就加上
        if(node==null) {
            arr[index]=new Node<>(key,value);
            size++;
            resize();
            return null;
        }
        //有就替换，返回老的V
        while(true){
            if(node.key.equals(key)){
                V res=node.value;
                //node=new Node<>(key,value); 这里毕竟key是一样的，直接修改value就可以
                node.value=value;
                return res;
            }
            if(node.next==null){
                node.next=new Node<>(key,value);
                size++;
                resize();
                return null;
            }
            node=node.next;
        }
    }

    public V remove(K key) {
        int index=indexof(key);
        //先拿到目标，然后随你处理
        Node<K,V> node= arr[index];
        //没有就空
        if(node==null) return null;
        
        //是头就移除头，不是就往后找
        if(node.key.equals(key)){
            arr[index]=node.next;
            size--;
            return node.value;
          }
          //其实不就是删除链表嘛
          Node<K,V> pre= node;
          Node<K,V> cur=node.next;

        while(true){
            if(cur==null) return null;
            if(cur.key.equals(key)){
                pre.next=cur.next;
                size--;
                return cur.value;
            }
            pre=pre.next;
            cur=cur.next;
        }
    }


//固定的长度，会导致大数据量时候链表太长，效率太差，所以扩容，当然肯定是自动扩容，有一个负载因子，超过了就扩，一般是0.75.
private void resize(){
if(this.size<arr.length*factor) return ;
//否则直接执行一个扩容，要搞个新的数组，容量一倍
int newlength=arr.length*2;
 Node<K,V>[] newarr=new Node[newlength];
 //遍历旧的，空就跳下一个，非空就看有没有头结点，没有就直接加进去，有就用next连上去
 for (Node<K,V> node : arr) {
  if(node==null) continue;

  Node<K,V> cur =node;
  while(cur!=null){
    int newindex= cur.key.hashCode()&(newlength-1);

    if(newarr[newindex]==null){
        newarr[newindex]=cur;
        //应该是独立的node，毕竟每一个node是独立判断的，不能把链表后面的也给带过去。但是，还要防止链表断，存一下next
        Node<K,V> next=cur.next;
        cur.next=null;
        cur=next;
    }else{
        Node<K,V> next =cur.next;
        cur.next=newarr[newindex];
        newarr[newindex]=cur;
        cur=next;
    }
  }  
 }

this.arr=newarr;

}
    public int indexof (K key){
     return key.hashCode()&(arr.length-1);
    }

    class Node<K,V>{
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        K key;
        V value;
        Node<K,V> next;
    }
}
