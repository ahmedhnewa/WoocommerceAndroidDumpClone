package com.alreyada.app.ui.fragment;

import androidx.fragment.app.Fragment;

public class ChatFragment extends Fragment /*implements MessageViewHolder.MessageViewHolderListener*/ {
   /* private static final String TAG = "ChatFragment";
    public static final String ANONYMOUS = "مجهول";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    private static final int MSG_TYPE_RIGHT = 1;
    private static final int MSG_TYPE_LEFT = 2;

    private Context context;
    private Activity activity;
    private FragmentChatBinding binding;
    private ProgressBar progressBar;

    private RecyclerView chatRv;
    private EditText messageEt;
    private ImageButton sendBtn;
    private LinearLayout chatLayout;
    private SessionManager sessionManager;

    private GoogleSignInClient mSignInClient;
    private LinearLayoutManager mLinearLayoutManager;
    // Firebase instance variables
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference, messagesRef;
    private FirebaseRecyclerAdapter<Message, MessageViewHolder> adapter;
    private boolean isCanLoadChatNow = false;
    private FirebaseAuthentication authentication;
    private String firstName, lastName, phoneNumber, photoUrl;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        activity = getActivity();

        initView();
        initVar();

        // check if the user is signed in (Firebase Authentication)
        // if not logged in , finish the activity
        if (auth.getCurrentUser() == null) {
            activity.finish();
            return;
        }

        if (sessionManager.isLoggedIn()) {
            firstName = sessionManager.getUser().getFirstName();
            lastName = sessionManager.getUser().getLastName();
            photoUrl = sessionManager.getUser().getImgUrl();
        } else {
            firstName = authentication.getAnonymousData().getFirstName();
            lastName = authentication.getAnonymousData().getLastName();
            phoneNumber = authentication.getAnonymousData().getPhoneNumber();
            photoUrl = null;
        }

        messagesRef = reference.child(Constants.MESSAGES_KEY).child(authentication.getUserUid());
        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    sendBotFirstMsg();
                } else {
                    isCanLoadChatNow = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                isCanLoadChatNow = false;
            }
        });

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(messagesRef, Message.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {
                progressBar.setVisibility(View.GONE);
                holder.bindMessage(model);
                if (model.getSenderUid().equals(authentication.getUserUid())) {
                    holder.setOnItemClickListener(ChatFragment.this);
                }
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view;
                if (viewType == MSG_TYPE_RIGHT) {
                    view = inflater.inflate(R.layout.row_chat_right, parent, false);
                } else {
                    view = inflater.inflate(R.layout.row_chat_left, parent, false);
                }
                return new MessageViewHolder(view);
            }

            @Override
            public int getItemViewType(int position) {
                Message item = adapter.getItem(position);
                String userUid = authentication.getUserUid();
                if (item.getSenderUid().equals(userUid)) {
                    return MSG_TYPE_RIGHT;
                } else {
                    return MSG_TYPE_LEFT;
                }
            }

            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError(error);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "تعذر تحميل المحادثة " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setStackFromEnd(true);
        chatRv.setLayoutManager(mLinearLayoutManager);
        chatRv.setAdapter(adapter);

        // Scroll down when a new message arrives
        // See MyScrollToBottomObserver.java for details
        adapter.registerAdapterDataObserver(
                new ChatScrollToBottomObserver(chatRv, adapter, mLinearLayoutManager));

        // Disable the send button when there's no text in the input field
        // See MyButtonObserver.java for details
        messageEt.addTextChangedListener(new ChatButtonObserver(sendBtn));

        // When the send button is clicked, send a text message
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = messageEt.getText().toString().trim();
                String key = messagesRef.push().getKey();
                getFormattedDate();

                Message message = new
                        Message(authentication.getUserUid(),
                        null,
                        msg,
                        getFormattedDate(),
                        firstName,
                        lastName,
                        photoUrl
                );

                messagesRef.child(key).setValue(message);
                messageEt.setText("");
            }
        });

        // When the image button is clicked, launch the image picker
        *//*mBinding.addMessageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });*//*
    }

    private void sendBotFirstMsg() {
        Message message = new Message("bot",
                null,
                "مرحبا بك " + firstName + " , للتواصل معنا اكتب راسلتك وارسلها وسنحاول الرد باسرع وقت ممكن " ,
                getFormattedDate(),
                "الرد ",
                "الالي",
                null);
        String key = messagesRef.push().getKey();
        messagesRef.child(key).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    isCanLoadChatNow = true;
                } else {
                    Utils.showErrorMessage("تعذر تحميل الرسائل", activity);
                    isCanLoadChatNow = false;
                }
            }
        });
    }

    private String getFormattedDate() {
        Date c = Calendar.getInstance().getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy K:mm a", Locale.ENGLISH); // old hh:mm a
        String localTime = date.format(c);
        return localTime;
    }

    @Override
    public void onPause() {
        adapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }


    private void initVar() {
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        sessionManager = new SessionManager(context);
        authentication = new FirebaseAuthentication(context);
        configGoogleSignInOptions();
    }

    private void configGoogleSignInOptions() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(context, gso);
    }

    private void initView() {
        chatRv = binding.recyclerView;
        messageEt = binding.messageEt;
        sendBtn = binding.sendBtn;
        progressBar = binding.progressBar;
        chatLayout = binding.chatLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDeleteClick(View view, int position) {
        Message item = adapter.getItem(position);
        String key = adapter.getRef(position).getKey();
        messagesRef.child(key).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Utils.showSuccessMessage("تم حذف الرسالة بنجاح", activity);
                } else {
                    Utils.showErrorMessage("تعذر حذف الرسالة " + error.getMessage(), activity);
                }
            }
        });
    }

    @Override
    public void onCopyClick(View view, int position) {
        String msg = adapter.getItem(position).getMessage();
        Utils.copyText("الرسالة", msg, context);
        Toast.makeText(context, "تم النسخ الى الحافظة", Toast.LENGTH_SHORT).show();
    }
*/
}
