const message = {
  //   state() {
  //     return {
  //       messageTimestamp: 0,
  //     };
  //   },
  state: {
    messageTimestamp: 1,
  },
  mutations: {
    UPDATE_MESSAGE(state) {
      state.messageTimestamp++;
    },
  },

  actions: {},
};
