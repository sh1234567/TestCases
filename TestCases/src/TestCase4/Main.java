package TestCase4;

import java.io.IOException;
import java.util.*;
import com.rits.cloning.Cloner;

public class Main {
	public static void main(String[] args) throws CloneNotSupportedException {
		String mode = "d";
		Queue<State> queue = new LinkedList<State>();
		Queue<State> queue_2 = new LinkedList<State>();
		MessageQueue<Message> mq = new MessageQueue<Message>();
		Cloner cloner = new Cloner();
		float t_1 = 0;
		float t_2 = 0;
		int id = 0;
		int states_num = 0;
		Message a = new Message();
		Actors[] actors = new Actors[10];
		String[] actorsNames = new String[10];
		int actorId = 0;
		Ping pi = new Ping("pi", actorId, mq);
		actors[actorId] = pi;
		actorsNames[actorId] = "pi";
		actorId += 1;
		Pong po = new Pong("po", actorId, mq);
		actors[actorId] = po;
		actorsNames[actorId] = "po";
		actorId += 1;
		State s_0 = new State();
		s_0.setActors(actors);
		s_0.setMessageQueue(mq);
		queue.add(s_0);
		states_num += 1;
		System.out.println(printState(s_0, states_num));
		int n = 0;
		if (mode.equals("d")) {
			while (!queue.isEmpty() && states_num < 70) {
				State s_1 = new State();
				State s_2 = new State();
				s_1 = queue.poll();
				queue_2.add(s_1);
				State s = cloner.deepClone(s_1);
				if (!s.getMessageQueue().isEmpty() && s.getMessageQueue().peek() != null) {
					float after = s.getMessageQueue().peek().getAfter_1();
					t_1 = after;
					t_2 = t_1;
					int highPriority_num = 0;
					MessageQueue<Message> mq_2 = new MessageQueue<Message>();
					while (!s.getMessageQueue().isEmpty() && s.getMessageQueue().peek().getAfter_1() == after) {
						mq_2.add(s.getMessageQueue().remove());
						highPriority_num += 1;
					}
					Message[] equalPriorityMsgs = new Message[highPriority_num];
					for (int i = 0; i < highPriority_num; i++) {
						equalPriorityMsgs[i] = mq_2.remove();
					}

					for (int i = 0; i < equalPriorityMsgs.length; i++) {
						State new_s = cloner.deepClone(s);
						MessageQueue<Message> mq_3 = new MessageQueue<Message>();
						for (int j = 0; j < equalPriorityMsgs.length; j++) {
							if (i != j) {
								new_s.getMessageQueue().add(equalPriorityMsgs[j]);
							} else {
								a = equalPriorityMsgs[j];
							}
						}
						id = 0;
						for (int j = 0; j < actorsNames.length; j++) {
							if (actorsNames[j] != null && actorsNames[j].equals("pi")) {
								id = j;
								break;
							}
						}
						if (a.getReceiver().equals("pi") && a.getMsgName().equals("ping")
								&& actors[id].getClass().getSimpleName().equals("Ping")) {
							s_2 = ((Ping) new_s.getActors()[id]).ping(t_1, t_2, new_s, mode);
							if (!contains(queue, s_2, mode) && !contains(queue_2, s_2, mode)) {
								queue.add(s_2);
								states_num += 1;
								System.out.println(printState(s_2, states_num));
							} else
								System.out.println("equal");
						}
						id = 0;
						for (int j = 0; j < actorsNames.length; j++) {
							if (actorsNames[j] != null && actorsNames[j].equals("po")) {
								id = j;
								break;
							}
						}
						if (a.getReceiver().equals("po") && a.getMsgName().equals("pong")
								&& actors[id].getClass().getSimpleName().equals("Pong")) {
							s_2 = ((Pong) new_s.getActors()[id]).pong(t_1, t_2, new_s, mode);
							if (!contains(queue, s_2, mode) && !contains(queue_2, s_2, mode)) {
								queue.add(s_2);
								states_num += 1;
								System.out.println(printState(s_2, states_num));
							} else
								System.out.println("equal");
						}
						id = 0;
						for (int j = 0; j < actorsNames.length; j++) {
							if (actorsNames[j] != null && actorsNames[j].equals("po")) {
								id = j;
								break;
							}
						}
						if (a.getReceiver().equals("po") && a.getMsgName().equals("pong2")
								&& actors[id].getClass().getSimpleName().equals("Pong")) {
							s_2 = ((Pong) new_s.getActors()[id]).pong2(t_1, t_2, new_s, mode);
							if (!contains(queue, s_2, mode) && !contains(queue_2, s_2, mode)) {
								queue.add(s_2);
								states_num += 1;
								System.out.println(printState(s_2, states_num));
							} else
								System.out.println("equal");
						}
					}
				} else {
					queue.poll();
				}
				n += 1;
			}
		}
	}

	private static boolean contains(Queue<State> queue, State s_1, String mode) {
		// TODO Auto-generated method stub
		Iterator i = queue.iterator();
		while (i.hasNext()) {
			State s = (State) i.next();
			if (s_1.equals(s, mode))
				return true;
		}
		return false;
	}

	private static String printState(State s, int state_number) {
		String retValue = "";
		retValue += "-------------------------------------------------------------------------\r\n";
		retValue += "State number: " + state_number + "\r\n";
		MessageQueue<Message> mq = s.getMessageQueue();
		Actors[] actors = s.getActors();
		Iterator<Message> itr = mq.iterator();
		retValue += "messageQueue contents: \r\n";
		while (itr.hasNext()) {
			Message msg = itr.next();
			if (msg != null) {
				retValue += "MsgName:" + msg.getMsgName() + ", " + "MsgSender:" + msg.getSender() + ", "
						+ "MsgReceiver:" + msg.getReceiver() + ", " + "MsgAfter:" + msg.getAfter_1() + "\r\n";
			}
		}
		retValue += "actors variables: \r\n";

		for (int i = 0; i < actors.length; i++) {
			if (actors[i] != null) {
				if (actors[i].getClass().getSimpleName().equals("Ping")) {
					Ping a = (Ping) actors[i];
					retValue += "Actor's Id:" + a.getId() + ", class:" + a.getClass().getSimpleName() + ", name:"
							+ a.getName() + ", ";
					retValue += "\r\n";
				}
				if (actors[i].getClass().getSimpleName().equals("Pong")) {
					Pong a = (Pong) actors[i];
					retValue += "Actor's Id:" + a.getId() + ", class:" + a.getClass().getSimpleName() + ", name:"
							+ a.getName() + ", ";
					retValue += "i:" + a.geti() + ", ";
					retValue += "\r\n";
				}
			}
		}
		retValue += "-------------------------------------------------------------------------";
		DirectoryCreator directoryCreator = new DirectoryCreator();
		try {
			directoryCreator.addFile("a.txt", retValue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retValue;
	}

	private static String printStatesQueue(Queue<State> q) {
		String retValue = "";
		Iterator<State> itr = q.iterator();
		int i = 1;
		while (itr.hasNext()) {
			retValue += printState(itr.next(), i);
			i++;
		}
		DirectoryCreator directoryCreator = new DirectoryCreator();
		try {
			directoryCreator.addFile("a.txt", retValue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retValue;
	}
}